package com.seal.validator.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class StringUtils
{
    public static @NotNull Map<String, String> toMap(String string) {
        Map<String, String> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(string, ",");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split("=");
            map.put(keyValue[0].trim(), keyValue[1].trim());
        }

        return map;
    }
}
