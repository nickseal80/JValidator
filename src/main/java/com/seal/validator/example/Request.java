package com.seal.validator.example;

import com.seal.validator.Validable;
import com.seal.validator.annotation.validation.MinLength;
import com.seal.validator.annotation.validation.Required;

public class Request implements Validable
{
    @Required
    @MinLength(min = 2, fieldName = "Nickname")
    private String name;

    @Required
    @MinLength(message = "Field '$fieldName$' must have at least $min$ character", min = 5, fieldName = "Email")
    private String email;

    public Request() {}

    public Request(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
