package com.seal.validator.rule.error;

import java.util.List;

public class FieldError
{
    private String field;
    private List<Error> errors;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
