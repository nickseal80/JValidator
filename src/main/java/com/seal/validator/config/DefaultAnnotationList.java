package com.seal.validator.config;

import com.seal.validator.annotation.Store;
import com.seal.validator.annotation.validation.*;

import java.util.ArrayList;
import java.util.List;

@Store
public class DefaultAnnotationList
{
    private final List<Class<?>> annotations = new ArrayList<>();

    public DefaultAnnotationList() {
        annotations.add(Required.class);
        annotations.add(Email.class);
        annotations.add(MinLength.class);
        annotations.add(MaxLength.class);
        annotations.add(Min.class);
        annotations.add(Max.class);
    }

    public List<Class<?>> getAnnotations() {
        return annotations;
    }
}
