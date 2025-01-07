package com.seal.validator;

import com.seal.validator.annotation.validation.MinLength;
import com.seal.validator.annotation.validation.Required;
import com.seal.validator.config.ConfigBuilder;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import com.seal.validator.util.Annotation;
import com.seal.validator.util.JAnnotation;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Validator
{
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private final Configuration config = new ConfigBuilder();

    public Validator() {}

    public Validator(String packageName) {
        try {
            config.forPackage(packageName).build();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void init(Validable validableClass) throws IllegalAccessException {
//        Reflections reflections = new Reflections(config.getPackage());
//        Set<Class<? extends Validable>> implementationClasses = reflections.getSubTypesOf(Validable.class);

//        if (config.getMode().equals(Configuration.MODE_DEBUG)) {
//            logger.info("Found {} implementations", implementationClasses.size());
//            if (!implementationClasses.isEmpty()) {
//                logger.info(implementationClasses.toString());
//            }
//        }

        if (validableClass != null) {
            validate(validableClass);
        } else {
            if (config.getMode().equals(Configuration.MODE_DEBUG)) {
                logger.warn("No implementations found");
            }
        }
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
                validateField(field);
            }
        }
    }

    private void validateField(@NotNull Field field) throws IllegalAccessException {
        field.setAccessible(true);

        try {
            for (java.lang.annotation.Annotation annotation : field.getAnnotations()) {
                Map<String, Object> args = new HashMap<>();
                for (Method method : annotation.annotationType().getDeclaredMethods()) {
                    args.put(method.getName() ,method.invoke(annotation, (Object[]) null));
                }

                System.out.printf(args.toString());
            }

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param clazz класс, реализующий интерфейс Validable
     * @return валидируемые поля с их аннотациями
     */
    public Map<Field, List<Class<?>>> getAnnotatedFields(@NotNull Class<? extends Validable> clazz) {
        Map<Field, List<Class<?>>> fields = new HashMap<>();
        Annotation annotation = new JAnnotation();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            List<Class<?>> annotations = annotation.matches(field.getAnnotations());
            fields.put(field, annotations);
            field.setAccessible(false);
        }

        return fields;
    }
}
