package com.vibent.vibentback.common.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email field) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        if (field == null) {
            return true;
        }
        return field.matches("^(.+)@(.+)$");
    }

}