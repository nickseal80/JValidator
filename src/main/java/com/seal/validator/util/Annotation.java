package com.seal.validator.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Annotation
{
    void addAnnotation(Class<?> annotation);

    boolean AnnotationContains(Class<?> annotation);

    List<Class<?>> getAnnotationList();

    List<Class<?>> matches(java.lang.annotation.Annotation @NotNull [] annotations);
}
