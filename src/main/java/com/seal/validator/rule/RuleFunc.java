package com.seal.validator.rule;

import java.lang.reflect.Field;

@FunctionalInterface
public interface RuleFunc
{
    boolean isValid(Field field);
}
