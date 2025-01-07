package com.seal.validator.rule;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Rules
{
    private Map<String, RuleFunc> rules = new HashMap<>();

    public Rules() {
        rules.put("required", (field) -> {
            // ...
            return false;
        });
    }
}
