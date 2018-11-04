package com.vibent.vibentback.common.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "invalid format - phone number must be between 8 and 20 characters and start with a '+' and the international code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}