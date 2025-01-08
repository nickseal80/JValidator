package com.seal.validator.rule;

import com.seal.validator.annotation.validation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Rules
{
    private final Map<String, RuleFunc> rules = new HashMap<>();

    public Rules() {
        rules.put(Required.class.getName(), (dataObject) ->
                (dataObject.getFieldValue() != null) && (!dataObject.getFieldValue().equals("")));

        /* string min/max */
        rules.put(MinLength.class.getName(), (ruleDataObject ->
                ((String) ruleDataObject.getFieldValue()).length() >= (int) ruleDataObject.getArgs().get("min")));

        rules.put(MaxLength.class.getName(), (ruleDataObject ->
                ((String) ruleDataObject.getFieldValue()).length() <= (int) ruleDataObject.getArgs().get("max")));

        /* number min/max */
        rules.put(Min.class.getName(), (ruleDataObject ->
                (int) ruleDataObject.getFieldValue() >= (int) ruleDataObject.getArgs().get("min")));

        rules.put(Min.class.getName(), (ruleDataObject ->
                (short) ruleDataObject.getFieldValue() >= (short) ruleDataObject.getArgs().get("min")));

        rules.put(Min.class.getName(), (ruleDataObject ->
                (long) ruleDataObject.getFieldValue() >= (long) ruleDataObject.getArgs().get("min")));

        rules.put(Min.class.getName(), (ruleDataObject ->
                (float) ruleDataObject.getFieldValue() >= (float) ruleDataObject.getArgs().get("min")));

        rules.put(Min.class.getName(), (ruleDataObject ->
                (double) ruleDataObject.getFieldValue() >= (double) ruleDataObject.getArgs().get("min")));

        rules.put(Max.class.getName(), (ruleDataObject ->
                (int) ruleDataObject.getFieldValue() <= (int) ruleDataObject.getArgs().get("max")));

        rules.put(Max.class.getName(), (ruleDataObject ->
                (short) ruleDataObject.getFieldValue() <= (short) ruleDataObject.getArgs().get("max")));

        rules.put(Max.class.getName(), (ruleDataObject ->
                (long) ruleDataObject.getFieldValue() <= (long) ruleDataObject.getArgs().get("max")));

        rules.put(Max.class.getName(), (ruleDataObject ->
                (float) ruleDataObject.getFieldValue() <= (float) ruleDataObject.getArgs().get("max")));

        rules.put(Max.class.getName(), (ruleDataObject ->
                (double) ruleDataObject.getFieldValue() <= (double) ruleDataObject.getArgs().get("max")));

        rules.put(Email.class.getName(), (ruleDataObject -> {
            Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
            return pattern.matcher((String) ruleDataObject.getFieldValue()).matches();
        }));
    }

    public Map<String, RuleFunc> getRules() {
        return rules;
    }
}
