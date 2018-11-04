package com.vibent.vibentback.common.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "invalid format - password must be between 8 and 50 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}