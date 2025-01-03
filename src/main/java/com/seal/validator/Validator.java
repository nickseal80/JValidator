package com.seal.validator;

import com.seal.validator.config.ConfigBuilder;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

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

    public void init() {
        Reflections reflections = new Reflections(config.getPackage());
        Set<Class<? extends Validable>> implementationClasses = reflections.getSubTypesOf(Validable.class);

        if (config.getMode().equals(Configuration.MODE_DEBUG)) {
            logger.info("Found {} implementations", implementationClasses.size());
            if (!implementationClasses.isEmpty()) {
                logger.info(implementationClasses.toString());
            }
        }

        if (!implementationClasses.isEmpty()) {
            validate(implementationClasses);
        } else {
            if (config.getMode().equals(Configuration.MODE_DEBUG)) {
                logger.warn("No implementations found");
            }
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public void validate(@NotNull Set<Class<? extends Validable>> classes) {
        for (Class<? extends Validable> clazz : classes) {
            getAnnotatedFields(clazz);
        }
    }

    /**
     * @param clazz класс, реализующий интерфейс Validable
     * @return валидируемые поля с их аннотациями
     */
    public Set<Field> getAnnotatedFields(@NotNull Class<? extends Validable> clazz) {
        Set<Field> fields = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

        }

        return fields;
    }
}
