package com.seal.validator.util;

import com.seal.validator.config.DefaultAnnotationList;
import com.seal.validator.exception.AnnotationContainsException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JAnnotation implements Annotation
{
    private final List<Class<?>> annotationsList;

    public JAnnotation() {
        DefaultAnnotationList defaultAnnotationList = new DefaultAnnotationList();
        annotationsList = defaultAnnotationList.getAnnotations();
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

    @Override
    public List<Class<?>> matches(java.lang.annotation.Annotation @NotNull [] annotations) {
        List<Class<?>> matches = new ArrayList<>();

        for (java.lang.annotation.Annotation annotation : annotations) {
            for (Class<?> annotationClass : annotationsList) {
                if (annotation.annotationType().equals(annotationClass)) {
                    matches.add(annotationClass);
                }
            }
        }

        return matches;
    }
}
