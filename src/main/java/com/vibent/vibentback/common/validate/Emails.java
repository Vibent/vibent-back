package com.vibent.vibentback.common.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Emails {
    String message() default "invalid format - should all be email addresses";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}