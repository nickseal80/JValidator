package com.seal.validator.rule;

import com.seal.validator.annotation.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RulesTest
{
    private Map<String, RuleFunc> rules;
    private RuleDataObject ruleData;

    @BeforeEach
    void setUp() {
        ruleData = new RuleDataObject();
        Rules rules = new Rules();
        this.rules = rules.getRules();
    }

    /* Required */
    @Test
    public void RequiredRuleTest() {
        RuleFunc requiredFunc = rules.get(Required.class.getName());
        ruleData.setFieldValue("value is required");
        assertNotNull(requiredFunc);
        assertTrue(requiredFunc.isValid(ruleData));
    }

    @Test
    public void RequiredRuleTestFailure() {
        RuleFunc requiredFunc = rules.get(Required.class.getName());

        /* Empty value */
        ruleData.setFieldValue("");
        assertFalse(requiredFunc.isValid(ruleData));

        /* Null value */
        ruleData.setFieldValue(null);
        assertFalse(requiredFunc.isValid(ruleData));
    }

    /* Email */
    @Test
    public void EmailRuleTest() {
        RuleFunc emailFunc = rules.get(Email.class.getName());
        assertNotNull(emailFunc);

        /* Simple email */
        ruleData.setFieldValue("test@test.com");
        assertTrue(emailFunc.isValid(ruleData));

        /* Difficult email */
        ruleData.setFieldValue("test.1234_saira@testament.com");
        assertTrue(emailFunc.isValid(ruleData));

        /* Nullable email */
        ruleData.setFieldValue(null);
        assertTrue(emailFunc.isValid(ruleData));

        /* Empty email */
        ruleData.setFieldValue("");
        assertTrue(emailFunc.isValid(ruleData));
    }

    @Test
    public void EmailRuleTestFailure() {
        RuleFunc emailFunc = rules.get(Email.class.getName());
        assertNotNull(emailFunc);

        /* Incorrect email */
        ruleData.setFieldValue("test.test.com");
        assertFalse(emailFunc.isValid(ruleData));
    }

    /* Min length */
    @Test
    public void minLengthRuleTest() {
        RuleFunc minLengthFunc = rules.get(MinLength.class.getName());
        assertNotNull(minLengthFunc);

        ruleData.setFieldValue("long string");
        ruleData.setArgs(Map.of("min", 5, "message", "message string"));
        assertTrue(minLengthFunc.isValid(ruleData));

        /* By last symbol */
        ruleData.setArgs(Map.of("min", 11, "message", "message string"));
        assertTrue(minLengthFunc.isValid(ruleData));
    }

    @Test
    public void minLengthRuleTestFailure() {
        RuleFunc minLengthFunc = rules.get(MinLength.class.getName());
        assertNotNull(minLengthFunc);

        ruleData.setFieldValue("Bo");
        ruleData.setArgs(Map.of("min", 5, "message", "message string"));
        assertFalse(minLengthFunc.isValid(ruleData));

        ruleData.setArgs(Map.of("min", 3, "message", "message string"));
        assertFalse(minLengthFunc.isValid(ruleData));
    }

    /* Max length */
    @Test
    public void maxLengthRuleTest() {
        RuleFunc maxLengthFunc = rules.get(MaxLength.class.getName());
        assertNotNull(maxLengthFunc);

        ruleData.setFieldValue("Very long string");
        ruleData.setArgs(Map.of("max", 18, "message", "message string"));
        assertTrue(maxLengthFunc.isValid(ruleData));

        ruleData.setArgs(Map.of("max", 16, "message", "message string"));
        assertTrue(maxLengthFunc.isValid(ruleData));
    }

    @Test
    public void maxLengthRuleTestFailure() {
        RuleFunc maxLengthFunc = rules.get(MaxLength.class.getName());
        assertNotNull(maxLengthFunc);

        ruleData.setFieldValue("Very long string");
        ruleData.setArgs(Map.of("max", 15, "message", "message string"));
        assertFalse(maxLengthFunc.isValid(ruleData));

        ruleData.setArgs(Map.of("max", 0, "message", "message string"));
        assertFalse(maxLengthFunc.isValid(ruleData));
    }

    /* Numeric min */
    @Test
    public void minIntRuleTest() {
        RuleFunc minIntFunc = rules.get(MinInt.class.getName());
        assertNotNull(minIntFunc);

        ruleData.setFieldValue(5);
        ruleData.setArgs(Map.of("min", 3, "message", "message string"));
        assertTrue(minIntFunc.isValid(ruleData));

        ruleData.setArgs(Map.of("min", 5, "message", "message string"));
        assertTrue(minIntFunc.isValid(ruleData));
    }

    @Test
    public void minIntRuleTestFailure() {
        RuleFunc minIntFunc = rules.get(MinInt.class.getName());
        assertNotNull(minIntFunc);

        ruleData.setFieldValue("5");
        ruleData.setArgs(Map.of("min", 3, "message", "message string"));
        assertFalse(minIntFunc.isValid(ruleData));

        ruleData.setFieldValue(4);
        ruleData.setArgs(Map.of("min", 5, "message", "message string"));
        assertFalse(minIntFunc.isValid(ruleData));
    }

    @Test
    public void maxIntRuleTest() {}

    @Test
    public void maxIntRuleTestFailure() {}

    @Test
    public void minFloatRuleTest() {}

    @Test
    public void minFloatRuleTestFailure() {}

    @Test
    public void maxFloatRuleTest() {}

    @Test
    public void maxFloatRuleTestFailure() {}

    @Test
    public void minDoubleRuleTest() {}

    @Test
    public void minDoubleRuleTestFailure() {}

    @Test
    public void maxDoubleRuleTest() {}

    @Test
    public void maxDoubleRuleTestFailure() {}

}