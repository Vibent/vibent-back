package com.vibent.vibentback.common.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class BCryptValidator implements ConstraintValidator<BCrypt, String> {

    @Override
    public void initialize(BCrypt field) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        boolean isOk = field.matches("^\\$2[aby]?\\$\\d{1,2}\\$[./A-Za-z0-9]{53}$");
        if(!isOk)
            log.error("Invalid password : {}", field);
        return isOk;
    }

}