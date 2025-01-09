package com.seal.validator.rule;

/**
 * A functional interface representing a validation rule function.
 * This interface is designed to validate a {@link RuleDataObject}
 * and determine whether it satisfies certain criteria.
 *
 * <p>
 * Since this is a functional interface, it can be implemented using a lambda expression
 * or a method reference. Example usage:
 * </p>
 * <pre>
 * {@code
 * RuleFunc rule = ruleDataObject -> ruleDataObject.getValue() > 0;
 * boolean isValid = rule.isValid(new RuleDataObject(5));
 * }
 * </pre>
 *
 * @FunctionalInterface
 * Indicates that this interface is intended to be used as a functional interface,
 * having exactly one abstract method.
 */
@FunctionalInterface
public interface RuleFunc
{
    /**
     * Validates the given {@link RuleDataObject} based on custom logic.
     *
     * @param ruleDataObject the data object to validate.
     * @return {@code true} if the data object satisfies the rule, {@code false} otherwise.
     */
    boolean isValid(RuleDataObject ruleDataObject);
}
