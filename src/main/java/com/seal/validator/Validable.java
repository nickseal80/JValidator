package com.seal.validator;

import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;
import com.seal.validator.rule.ValidatorResponse;

/**
 * The {@code Validable} interface defines a contract for classes that can be validated using the {@link Validator}.
 * Implementing classes should provide fields with validation annotations, and this interface provides a default
 * method for performing the validation of such classes.
 *
 * @see Validator
 */
public interface Validable
{
    /**
     * Validates the given {@link Validable} object using the default configuration.
     * This method uses the {@link Validator} class to perform validation and returns a {@link ValidatorResponse}
     * based on the results of the validation.
     * <p>
     * The validation mode is set to {@code MODE_DEBUG} for the package {@code "com.seal.validator.example"}.
     * </p>
     *
     * @param validableClass the object to be validated. This should be an instance of a class that implements
     *                       the {@code Validable} interface and has validation annotations on its fields.
     * @return a {@link ValidatorResponse} containing the result of the validation, including status code and errors if any.
     * @throws ConfigurationException if there is an error with the configuration setup of the validator.
     * @throws IllegalAccessException if there is an issue accessing the fields of the validable class during validation.
     */
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
