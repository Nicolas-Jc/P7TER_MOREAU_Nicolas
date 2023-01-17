package com.openclassrooms.poseidon.configuration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordConstraintValidatorTest {

    private final PasswordConstraintValidator validator = new PasswordConstraintValidator();

    @Nested
    class PasswordValidFlow {
        @Test
        void isValid_shouldReturnTrue() {
            assertTrue(isValid("Password1@"));

        }
    }

    @Nested
    class PasswordNotValidFlow {
        @Test
        void isNotValid_shouldReturnFalse_() {
            assertFalse(isValid("password"));
            assertFalse(isValid("Password"));
            assertFalse(isValid("Password1"));
            assertFalse(isValid("Pp1@"));
        }
    }

    private boolean isValid(String value) {
        return validator.isValid(value, null);
    }
}