package com.vibent.vibentback.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BCryptValidator implements ConstraintValidator<BCrypt, String> {

    @Override
    public void initialize(BCrypt field) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return field.matches("^\\$2[aby]?\\$\\d{1,2}\\$[./A-Za-z0-9]{53}$");
    }

}