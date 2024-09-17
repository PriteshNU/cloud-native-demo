package com.cloudnative.webapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowNullButNotEmptyValidator implements ConstraintValidator<AllowNullButNotEmpty, String> {

    @Override
    public void initialize(AllowNullButNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isEmpty();
    }
}
