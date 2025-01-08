package com.seal.validator.rule;

import com.seal.validator.rule.error.ErrorList;

import java.net.http.HttpResponse;

public class ValidatorResponse
{
    private int statusCode;
    private ErrorList errorList;

    public ValidatorResponse() {}

    public ValidatorResponse(int statusCode, ErrorList errorList) {
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ErrorList getErrorList() {
        return errorList;
    }

    public void setErrorList(ErrorList errorList) {
        this.errorList = errorList;
    }
}
