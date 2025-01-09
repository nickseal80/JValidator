package com.seal.validator.annotation.validation;

import com.seal.validator.Validable;
import com.seal.validator.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code Email} annotation is used to mark fields that should be validated as containing a valid email address.
 * This annotation can be applied to fields within classes that require email validation.
 * <p>
 * By default, the message associated with the annotation indicates that the email is not valid.
 * </p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * public class User {
 *     @Email(message = "Please provide a valid email address")
 *     private String email;
 * }
 * </pre>
 *
 * @see Validator
 * @see Validable
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Email
{
    /**
     * Specifies the error message to be used if the email validation fails.
     * The default message is "Email is not valid".
     *
     * @return the custom error message if validation fails
     */
    String message() default "Email is not valid";
}
