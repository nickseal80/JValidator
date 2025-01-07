package com.seal.validator;

import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;

public interface Validable
{
    default void validate(Validable validableClass) throws ConfigurationException, IllegalAccessException {
        Validator validator = new Validator();
        Configuration config = validator.getConfig();
        config
                .forPackage("com.seal.validator.example")
                .setMode(Configuration.MODE_DEBUG)
                .build();

        validator.init(validableClass);
    }
}
