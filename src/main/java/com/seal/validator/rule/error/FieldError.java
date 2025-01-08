package com.seal.validator.rule.error;

import java.util.ArrayList;
import java.util.List;

public class FieldError
{
    private String field;
    private List<Error> errors = new ArrayList<>();

    public FieldError(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void addError(Error error) {
        errors.add(error);
    }
}
