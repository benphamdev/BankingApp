package com.banking.thejavabanking.dto.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        //validate phone numbers of format "0902345345"
        if (value.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces: 090-234-4567
        else if (value.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else //return false if nothing matches the input
            if (value.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
                //validating phone number where area code is in braces ()
            else return value.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}");
    }
}
