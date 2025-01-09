package com.seal.validator;

import com.seal.validator.config.ConfigBuilder;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import com.seal.validator.exception.NoImplementationFoundException;
import com.seal.validator.exception.ValidationRuleNotFoundException;
import com.seal.validator.rule.RuleDataObject;
import com.seal.validator.rule.RuleFunc;
import com.seal.validator.rule.Rules;
import com.seal.validator.rule.ValidatorResponse;
import com.seal.validator.rule.error.Error;
import com.seal.validator.rule.error.FieldError;
import com.seal.validator.util.JAnnotation;
import com.seal.validator.util.Message;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The {@code Validator} class is responsible for validating fields within classes that are annotated with validation annotations.
 * It supports the validation of various validation rules applied to the fields and generates a {@link ValidatorResponse} based on the validation results.
 * This class uses a configuration object to manage the validation modes and configurations.
 */
public class Validator
{
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private final Configuration config = new ConfigBuilder();

    private ValidatorResponse response;

    private final List<FieldError> errorList = new ArrayList<>();

    /**
     * Constructs a new {@code Validator} with default configuration.
     */
    public Validator() {}

    /**
     * Constructs a new {@code Validator} and initializes it with the configuration for a specific package.
     *
     * @param packageName the package name used to configure the validator
     * @throws RuntimeException if there is an error in the configuration process
     */
    public Validator(String packageName) {
        try {
            config.forPackage(packageName).build();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates a given {@link Validable} object by checking its annotated fields.
     * Generates a {@link ValidatorResponse} that contains validation results.
     *
     * @param validableClass the object to be validated
     * @return a {@link ValidatorResponse} containing the status code and validation errors, if any
     * @throws IllegalAccessException if any field in the validable class is inaccessible
     * @throws NoImplementationFoundException if no validable implementation is found
     */
    public ValidatorResponse validateRequest(Validable validableClass) throws IllegalAccessException {
        if (validableClass != null) {
            validate(validableClass);
            ValidatorResponse response = new ValidatorResponse();
            if (errorList.isEmpty()) {
                response.setStatusCode(200);
            } else {
                response.setStatusCode(422);
                response.setErrorList(errorList);
            }

            return response;
        }

        throw new NoImplementationFoundException("No implementations found");
    }

    /**
     * Gets the configuration used by the validator.
     *
     * @return the {@link Configuration} object
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Validates the given {@link Validable} object by checking its fields and the associated annotations.
     * Logs validation details in debug mode.
     *
     * @param validableClass the object to be validated
     * @throws IllegalAccessException if any field in the validable class is inaccessible
     */
    public void validate(@NotNull Validable validableClass) throws IllegalAccessException {
        /*
         * @example
         * {class<?>:
         *     {private <T>Field:
         *         [
         *             interface Annotation<?>,
         *             interface Annotation<?>
         *             ...
         *         ],
         *     },
         *     ...
         * }
         */
        Map<Class<? extends Validable>, Map<Field, List<Class<?>>>> classMap = new HashMap<>();
        classMap.put(validableClass.getClass(), getAnnotatedFields(validableClass.getClass()));

        if (config.getMode().equals(Configuration.MODE_DEBUG)) {
            JSONObject jsonObject = new JSONObject(classMap);
            logger.debug(jsonObject.toString(3));
        }

        Map<Field, List<Class<?>>> fieldMap = classMap.get(validableClass.getClass());
        if (fieldMap != null) {
            for (Field field : fieldMap.keySet()) {
                validateField(field, validableClass);
            }
        }
    }

    /**
     * Validates a specific field within the given {@link Validable} object based on the field's annotations.
     * If validation fails, an error is added to the {@code errorList}.
     *
     * @param field the field to be validated
     * @param validable the object that contains the field to be validated
     * @throws IllegalAccessException if the field is inaccessible
     * @throws InvocationTargetException if an error occurs during annotation method invocation
     * @throws ValidationRuleNotFoundException if a validation rule is not found for the annotation
     */
    private void validateField(@NotNull Field field, Validable validable) throws IllegalAccessException {
        FieldError fieldError = new FieldError(field.getClass().getSimpleName());

        field.setAccessible(true);

        try {
            for (@NotNull Annotation annotation : field.getAnnotations()) {
                Map<String, Object> args = new HashMap<>();
                String messageTemp = "";
                String message = "";
                for (Method method : annotation.annotationType().getDeclaredMethods()) {
                    Object value = method.invoke(annotation);
                    args.put(method.getName() ,value);

                    if (method.getName().equals("message")) {
                        messageTemp = (String) value;
                    }
                }
                if (!messageTemp.isEmpty()) {
                    message = Message.build(args);
                }

                RuleDataObject ruleDataObject = new RuleDataObject();
                ruleDataObject.setFieldName(field.getName());
                ruleDataObject.setFieldValue(field.get(validable));
                ruleDataObject.setRuleType(annotation.annotationType().getName());
                ruleDataObject.setArgs(args);
                ruleDataObject.setMessage(message);

                Rules rules = new Rules();
                Map<String, RuleFunc> ruleFuncMap = rules.getRules();

                RuleFunc func = ruleFuncMap.get(ruleDataObject.getRuleType());
                if (func != null) {
                    if (!func.isValid(ruleDataObject)) {
                        Error error = new Error();
                        error.setName(ruleDataObject.getRuleType());
                        error.setMessage(ruleDataObject.getMessage());

                        fieldError.addError(error);
                    }
                } else {
                    throw new ValidationRuleNotFoundException("Validation rule not Found");
                }
            }

            if (!fieldError.getErrors().isEmpty()) {
                errorList.add(fieldError);
            }
        } catch (InvocationTargetException | ValidationRuleNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the annotated fields of a given class that implement the {@link Validable} interface.
     * This method inspects the annotations of the class fields and returns them in a map.
     *
     * @param clazz the class to inspect
     * @return a map where the key is a field and the value is a list of annotations on that field
     */
    public Map<Field, List<Class<?>>> getAnnotatedFields(@NotNull Class<? extends Validable> clazz) {
        Map<Field, List<Class<?>>> fields = new HashMap<>();
        JAnnotation annotation = new JAnnotation();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            List<Class<?>> annotations = annotation.matches(field.getAnnotations());
            fields.put(field, annotations);
            field.setAccessible(false);
        }

        return fields;
    }
}
