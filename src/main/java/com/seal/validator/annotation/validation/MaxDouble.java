package com.seal.validator.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the maximum allowable value for a double field.
 * This annotation can be applied to fields and is retained at runtime.
 *
 * <p>
 * It provides a mechanism to enforce validation on a field, ensuring that
 * its value does not exceed the specified maximum.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * {@code
 * public class Example {
 *     @MaxDouble(max = 100.0, message = "Value must be less than or equal to 100.0")
 *     private double value;
 * }
 * }
 * </pre>
 *
 * @Retention {@link RetentionPolicy#RUNTIME}
 * Indicates that the annotation is available at runtime via reflection.
 *
 * @Target {@link ElementType#FIELD}
 * Indicates that the annotation can only be applied to fields.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaxDouble
{
    /**
     * Specifies the maximum allowable value for the annotated field.
     *
     * @return the maximum value.
     */
    double max();

    /**
     * Specifies the error message to be displayed if the value exceeds the maximum.
     * The placeholder {@code $max$} can be used in the message to refer to the maximum value.
     *
     * @return the custom error message.
     */
    String message() default "Value must be no more than $max$";
}
