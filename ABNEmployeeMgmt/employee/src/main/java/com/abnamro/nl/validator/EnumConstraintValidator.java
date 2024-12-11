package com.abnamro.nl.validator;

import com.abnamro.nl.constants.RolesEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to valid if the role provided by the user is valid
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumValidator, String> {
    private List<String> values = new ArrayList<String>();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            RolesEnum.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}