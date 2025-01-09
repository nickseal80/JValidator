package com.seal.validator.rule;

import com.seal.validator.annotation.validation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The {@code Rules} class provides a collection of predefined validation rules
 * using a mapping between rule names and their corresponding {@link RuleFunc} implementations.
 *
 * <p>
 * This class is designed to support various types of validations, such as checking string length,
 * numeric range constraints, and email format validation. The rules are stored in a {@link Map},
 * where the keys are the fully qualified class names of the rule annotations, and the values are
 * lambda expressions or method references implementing the {@link RuleFunc} interface.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * {@code
 * Rules rules = new Rules();
 * RuleFunc emailRule = rules.getRules().get(Email.class.getName());
 * boolean isValid = emailRule.isValid(new RuleDataObject("example@domain.com"));
 * }
 * </pre>
 */
public class Rules
{
    /**
     * A map containing rule names and their corresponding validation functions.
     * The keys are the fully qualified class names of rule annotations, and the values are {@link RuleFunc} instances.
     */
    private final Map<String, RuleFunc> rules = new HashMap<>();

    /**
     * Constructs a new {@code Rules} object and initializes the map with predefined validation rules.
     *
     * <p>
     * The following rules are registered:
     * </p>
     * <ul>
     *   <li>{@code Required} - Ensures the field value is not null or empty.</li>
     *   <li>{@code MinLength} - Ensures the string length is at least the specified minimum.</li>
     *   <li>{@code MaxLength} - Ensures the string length is at most the specified maximum.</li>
     *   <li>{@code MinInt}, {@code MinFloat}, {@code MinDouble} - Ensures numeric values are at least the specified minimum.</li>
     *   <li>{@code MaxInt}, {@code MaxFloat}, {@code MaxDouble} - Ensures numeric values are at most the specified maximum.</li>
     *   <li>{@code Email} - Validates that the field value matches a valid email format.</li>
     * </ul>
     */
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

    /**
     * Retrieves the map of rules.
     *
     * @return the map of rule names to their corresponding {@link RuleFunc} implementations.
     */
    public Map<String, RuleFunc> getRules() {
        return rules;
    }
}
