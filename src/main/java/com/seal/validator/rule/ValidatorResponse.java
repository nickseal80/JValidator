package com.seal.validator.rule;

import com.seal.validator.rule.error.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidatorResponse
{
    private int statusCode;
    private List<FieldError> errorList = new ArrayList<>();

    public ValidatorResponse() {}

    public ValidatorResponse(int statusCode, List<FieldError> errorList) {
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<FieldError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<FieldError> errorList) {
        this.errorList = errorList;
    }
}
