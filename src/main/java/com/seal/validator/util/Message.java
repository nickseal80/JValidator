package com.seal.validator.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message
{
    public static String build(Map<String, Object> args) {
        String message = (String) args.get("message");
        Pattern pattern = Pattern.compile("\\$.+?\\$");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String argName = matcher.group().substring(1, matcher.group().length() - 1);
            Object argValue = args.get(argName);

            if (argValue != null) {
                message = message.replace(matcher.group(), argValue.toString());
            }
        }

        return message;
    }
}
