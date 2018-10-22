package com.vibent.vibentback.common.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

@Slf4j
public class EmailsValidator implements ConstraintValidator<Emails, Set<String>> {

    @Override
    public void initialize(Emails constraintAnnotation) {

    }

    @Override
    public boolean isValid(Set<String> emails, ConstraintValidatorContext constraintValidatorContext) {
        if (emails == null) {
            return true;
        }
        for (String email : emails) {
            if (!email.matches("^(.+)@(.+)$"))
                return false;
        }
        return true;
    }
}