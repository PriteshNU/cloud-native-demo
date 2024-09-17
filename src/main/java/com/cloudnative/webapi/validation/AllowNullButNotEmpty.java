package com.cloudnative.webapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AllowNullButNotEmptyValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface AllowNullButNotEmpty {

    String message() default "The field must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
