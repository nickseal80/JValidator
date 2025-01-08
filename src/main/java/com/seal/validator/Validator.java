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
import com.seal.validator.rule.error.ErrorList;
import com.seal.validator.rule.error.FieldError;
import com.seal.validator.util.JAnnotation;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private final Configuration config = new ConfigBuilder();

    private ValidatorResponse response;

    private final List<FieldError> errorList = new ArrayList<>();

    public Validator() {}

    public Validator(String packageName) {
        try {
            config.forPackage(packageName).build();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

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

    public Configuration getConfig() {
        return config;
    }

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
                    message = buildMessage(args);
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

    private String buildMessage(Map<String, Object> args) {
        String message = (String) args.get("message");
        Pattern pattern = Pattern.compile("\\$.+?\\$");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String argName = matcher.group().substring(1, matcher.group().length() - 1);
            Object argValue = args.get(argName);

            if (argValue != null) {
                message = message.replace(matcher.group(), argValue.toString());
            }
        }

        return message;
    }

    /**
     * @param clazz класс, реализующий интерфейс Validable
     * @return валидируемые поля с их аннотациями
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
