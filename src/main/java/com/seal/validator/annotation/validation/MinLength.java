package com.seal.validator.annotation.validation;

import com.seal.validator.annotation.AnnotationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength
{
    int min() default 1;
    String fieldName();
    String message() default "Field must have at least $min$ character";
}
