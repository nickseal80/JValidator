package com.seal.validator.rule;

import com.seal.validator.annotation.validation.MinLength;
import com.seal.validator.annotation.validation.Required;

import java.util.HashMap;
import java.util.Map;

public class Rules
{
    private final Map<String, RuleFunc> rules = new HashMap<>();

    public Rules() {
        rules.put(Required.class.getName(), (dataObject) ->
                (dataObject.getFieldValue() != null) && (!dataObject.getFieldValue().equals("")));

        rules.put(MinLength.class.getName(), (ruleDataObject ->
                ((String) ruleDataObject.getFieldValue()).length() >= (int) ruleDataObject.getArgs().get("min")));

    }

    public Map<String, RuleFunc> getRules() {
        return rules;
    }
}
