package com.seal.validator.util;

import org.json.JSONObject;

public class Json
{
    public static String toJson(Object obj) {
        JSONObject jsonObject = new JSONObject(obj);
        return jsonObject.toString(3);
    }
}
