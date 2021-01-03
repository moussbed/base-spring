package com.mb.spring.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidator. class)
@Target(value = ElementType. FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {

    String message() default "Mauvais numéro ISBN" ; Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
