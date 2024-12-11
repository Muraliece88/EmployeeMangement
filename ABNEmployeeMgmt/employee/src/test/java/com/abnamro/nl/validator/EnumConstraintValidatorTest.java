package com.abnamro.nl.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnumConstraintValidatorTest {
    @InjectMocks
    private EnumConstraintValidator enumConstraintValidator;
    @Test
    void testNullValue() {
        assertFalse(enumConstraintValidator.isValid(null, null));
    }

    @Test
    void testValidEnumVal() {
        assertTrue(enumConstraintValidator.isValid("ADMIN", null));
        assertTrue(enumConstraintValidator.isValid("USER", null));
        assertFalse(enumConstraintValidator.isValid("INVALID_ROLE", null));
    }


}