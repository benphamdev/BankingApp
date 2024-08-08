package com.banking.thejavabanking.domain.users.dto.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD})
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumberConstraint {
    int min() default 10;

    String message() default "{Invalid phone number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
