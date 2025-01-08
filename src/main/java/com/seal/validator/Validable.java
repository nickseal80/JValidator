package com.seal.validator;

import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import com.seal.validator.rule.ValidatorResponse;

public interface Validable
{
    default ValidatorResponse validate(Validable validableClass) throws ConfigurationException, IllegalAccessException {
        Validator validator = new Validator();
        Configuration config = validator.getConfig();
        config
                .forPackage("com.seal.validator.example")
                .setMode(Configuration.MODE_DEBUG)
                .build();

        return validator.validateRequest(validableClass);
    }
}
