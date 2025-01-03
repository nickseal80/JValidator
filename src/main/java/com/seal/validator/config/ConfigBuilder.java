package com.seal.validator.config;

import com.seal.validator.exception.AnnotationContainsException;
import com.seal.validator.exception.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.*;

public class ConfigBuilder implements Configuration
{
    private String packageName;
    private String mode = Configuration.MODE_PRODUCTION;
    private final List<Class<?>> annotationsList;

    public ConfigBuilder() {
        DefaultAnnotationList defaultAnnotationList = new DefaultAnnotationList();
        annotationsList = defaultAnnotationList.getAnnotations();
    }

    @Override
    public ConfigBuilder forPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    @Override
    public ConfigBuilder setMode(String mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public void build() throws ConfigurationException {
        if (packageName == null) {
            throw new ConfigurationException("Package name cannot be null");
        }

        if (!Objects.equals(mode, Configuration.MODE_PRODUCTION) && !Objects.equals(mode, Configuration.MODE_DEBUG)) {
            throw new ConfigurationException("Mode must be 'production' or 'debug'");
        }
    }

    @Override
    public String getMode() {
        return mode;
    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public void addAnnotation(@NotNull Class<?> annotation) {
        if (annotation.isAnnotation()) {
            annotationsList.add(annotation);
        }

        if (annotationsList.contains(annotation)) {
            throw new AnnotationContainsException("Annotation '" + annotation.getName() + "' already exists");
        }
    }

    @Override
    public boolean AnnotationContains(Class<?> annotation) {
        return annotationsList.contains(annotation);
    }

    @Override
    public List<Class<?>> getAnnotationList() {
        return List.of();
    }

    public List<Class<?>> matches(Annotation @NotNull [] annotations) {
        List<Class<?>> matches = new ArrayList<>();

        for (Annotation annotation : annotations) {
            for (Class<?> annotationClass : annotationsList) {
                if (annotation.annotationType().equals(annotationClass)) {
                    matches.add(annotationClass);
                }
            }
        }

        return matches;
    }

    // TODO: перенести логику по аннотациям в utils.Annotation
}
