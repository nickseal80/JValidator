package com.seal.validator;

import com.seal.validator.config.ConfigBuilder;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import com.seal.validator.util.Annotation;
import com.seal.validator.util.JAnnotation;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
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

    public void init(Class<? extends Validable> clazz) {
//        Reflections reflections = new Reflections(config.getPackage());
//        Set<Class<? extends Validable>> implementationClasses = reflections.getSubTypesOf(Validable.class);

//        if (config.getMode().equals(Configuration.MODE_DEBUG)) {
//            logger.info("Found {} implementations", implementationClasses.size());
//            if (!implementationClasses.isEmpty()) {
//                logger.info(implementationClasses.toString());
//            }
//        }

        if (clazz != null) {
            validate(clazz);
        } else {
            if (config.getMode().equals(Configuration.MODE_DEBUG)) {
                logger.warn("No implementations found");
            }
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public void validate(@NotNull Class<? extends Validable> clazz) {
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
        classMap.put(clazz, getAnnotatedFields(clazz));


        if (config.getMode().equals(Configuration.MODE_DEBUG)) {
            JSONObject jsonObject = new JSONObject(classMap);
            logger.debug(jsonObject.toString(3));
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
        }

        return fields;
    }
}
