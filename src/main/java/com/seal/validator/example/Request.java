package com.seal.validator.example;

import com.seal.validator.Validable;
import com.seal.validator.annotation.validation.Required;

public class Request implements Validable
{
    @Required
    private String name;

    public Request() {}

    public Request(String name) {
        this.name = name;
    }
}
