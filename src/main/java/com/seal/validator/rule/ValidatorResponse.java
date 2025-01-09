package com.seal.validator.rule;

import com.seal.validator.rule.error.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ValidatorResponse} class represents the result of a validation process.
 * It contains a status code and a list of field errors encountered during validation.
 */
public class ValidatorResponse
{
    /**
     * The status code representing the outcome of the validation process.
     * For example, 200 might indicate success, while other codes can represent errors.
     */
    private int statusCode;

    /**
     * A list of {@link FieldError} objects representing the validation errors for specific fields.
     */
    private List<FieldError> errorList = new ArrayList<>();

    /**
     * Constructs an empty {@code ValidatorResponse} object with default values.
     */
    public ValidatorResponse() {}

    /**
     * Constructs a {@code ValidatorResponse} object with the specified status code and error list.
     *
     * @param statusCode the status code representing the validation outcome.
     * @param errorList the list of field errors encountered during validation.
     */
    public ValidatorResponse(int statusCode, List<FieldError> errorList) {
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    /**
     * Retrieves the status code.
     *
     * @return the status code representing the validation outcome.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode the status code to set, representing the validation outcome.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Retrieves the list of field errors.
     *
     * @return the list of {@link FieldError} objects encountered during validation.
     */
    public List<FieldError> getErrorList() {
        return errorList;
    }

    /**
     * Sets the list of field errors.
     *
     * @param errorList the list of {@link FieldError} objects to set.
     */
    public void setErrorList(List<FieldError> errorList) {
        this.errorList = errorList;
    }
}
