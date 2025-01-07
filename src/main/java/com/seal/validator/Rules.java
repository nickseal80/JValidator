package com.seal.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Rules
{
    private Map<String, Runnable> rules = new HashMap<>();

    public Rules() {
        init();
    }

    public void init() {
        //
    }
}
