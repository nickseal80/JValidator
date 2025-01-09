package com.seal.validator.example;

import com.seal.validator.rule.ValidatorResponse;
import com.seal.validator.rule.error.FieldError;

import java.util.List;

public class App
{
    public static void main(String[] args) {
        Request request = new Request("Seal", "example@example.com");

        try {
            ValidatorResponse resp = request.validate(request);

            if (resp.getStatusCode() == 200) {
                // ...
            } else if (resp.getStatusCode() == 422) {
                List<FieldError> errors = resp.getErrorList();
                for (FieldError error : errors) {
                    // ...
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
