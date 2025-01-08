package com.seal.validator.rule;

import java.util.Map;

public class RuleDataObject implements RuleData
{
    private String ruleType;
    private String fieldName;
    private Object fieldValue;
    private Map<String, Object> args;
    private String message;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RuleDataObject [ruleType=" + ruleType + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", args=" + args.toString() + ", message=" + message + "]\n";
    }
}
