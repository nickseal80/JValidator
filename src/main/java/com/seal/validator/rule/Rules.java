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
        rules.put(MinInt.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Integer) {
                return (int) ruleDataObject.getFieldValue() >= (int) ruleDataObject.getArgs().get("min");
            }

            return false;
        }));

        rules.put(MinFloat.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Float) {
                return (float) ruleDataObject.getFieldValue() >= (float) ruleDataObject.getArgs().get("min");
            }

            return false;
        }));

        rules.put(MinDouble.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Double) {
                return (double) ruleDataObject.getFieldValue() >= (double) ruleDataObject.getArgs().get("min");
            }

            return false;
        }));

        rules.put(MaxInt.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Integer) {
                return (int) ruleDataObject.getFieldValue() <= (int) ruleDataObject.getArgs().get("max");
            }

            return false;
        }));

        rules.put(MaxFloat.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Float) {
                return (float) ruleDataObject.getFieldValue() <= (float) ruleDataObject.getArgs().get("max");
            }

            return false;
        }));

        rules.put(MaxDouble.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() instanceof Double) {
                return (double) ruleDataObject.getFieldValue() <= (double) ruleDataObject.getArgs().get("max");
            }

            return false;
        }));

        rules.put(Email.class.getName(), (ruleDataObject -> {
            if (ruleDataObject.getFieldValue() != null && !ruleDataObject.getFieldValue().equals("")) {
                Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
                return pattern.matcher((String) ruleDataObject.getFieldValue()).matches();
            }

            return true;
        }));
    }

    public Map<String, RuleFunc> getRules() {
        return rules;
    }
}
