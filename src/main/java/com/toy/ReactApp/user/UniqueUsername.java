package com.toy.ReactApp.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {UniqueUsernameValidator.class}
)
public @interface UniqueUsername  {
    String message() default "Bu `Username` adresi başka birine ait. Başka bir `Username` deneyin";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
