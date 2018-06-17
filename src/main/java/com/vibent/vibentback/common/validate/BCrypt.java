package com.vibent.vibentback.common.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BCryptValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BCrypt {
    String message() default "Invalid format - should be BCrypt encrypted";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}