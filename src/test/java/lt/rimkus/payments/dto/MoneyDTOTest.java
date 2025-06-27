package lt.rimkus.payments.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MoneyDTO Validation Tests")
class MoneyDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Valid MoneyDTO scenarios")
    class ValidScenarios {

        @Test
        @DisplayName("Should pass validation when all fields are valid")
        void shouldPassValidationWhenAllFieldsAreValid() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("100.50"));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Valid MoneyDTO should not have validation errors");
        }

        @Test
        @DisplayName("Should pass validation with minimum valid amount")
        void shouldPassValidationWithMinimumValidAmount() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("0.01"));
            moneyDTO.setCurrency("USD");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Minimum valid amount should pass validation");
        }

        @Test
        @DisplayName("Should pass validation with maximum valid amount")
        void shouldPassValidationWithMaximumValidAmount() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("10000000.00"));
            moneyDTO.setCurrency("GBP");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Maximum valid amount should pass validation");
        }

        @Test
        @DisplayName("Should pass validation with whole number amount")
        void shouldPassValidationWithWholeNumberAmount() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("1000"));
            moneyDTO.setCurrency("JPY");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Whole number amount should pass validation");
        }
    }

    @Nested
    @DisplayName("Amount validation failures")
    class AmountValidationFailures {

        @Test
        @DisplayName("Should fail validation when amount is null")
        void shouldFailValidationWhenAmountIsNull() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(null);
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Null amount should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Money amount is required")),
                    "Should contain 'Money amount is required' message");
        }

        @Test
        @DisplayName("Should fail validation when amount is zero")
        void shouldFailValidationWhenAmountIsZero() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(BigDecimal.ZERO);
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Zero amount should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().contains("must be greater than or equal to 0.01")),
                    "Should contain minimum value validation message");
        }

        @Test
        @DisplayName("Should fail validation when amount is negative")
        void shouldFailValidationWhenAmountIsNegative() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("-10.50"));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Negative amount should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().contains("must be greater than or equal to 0.01")),
                    "Should contain minimum value validation message");
        }

        @Test
        @DisplayName("Should fail validation when amount exceeds maximum")
        void shouldFailValidationWhenAmountExceedsMaximum() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("10000000.01"));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Amount exceeding maximum should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().contains("must be less than or equal to 10000000.00")),
                    "Should contain maximum value validation message");
        }

        @ParameterizedTest
        @ValueSource(strings = {"100.123", "50.999", "1.001", "0.999"})
        @DisplayName("Should fail validation when amount has more than 2 decimal places")
        void shouldFailValidationWhenAmountHasMoreThanTwoDecimalPlaces(String invalidAmount) {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal(invalidAmount));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(),
                    "Amount with more than 2 decimal places should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Amount can have at most 2 decimal places")),
                    "Should contain decimal places validation message");
        }

        @Test
        @DisplayName("Should fail validation when amount has too many integer digits")
        void shouldFailValidationWhenAmountHasTooManyIntegerDigits() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("12345678901.00")); // 11 integer digits
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(),
                    "Amount with more than 10 integer digits should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Amount can have at most 2 decimal places")),
                    "Should contain digits validation message");
        }
    }

    @Nested
    @DisplayName("Currency validation failures")
    class CurrencyValidationFailures {

        @Test
        @DisplayName("Should fail validation when currency is null")
        void shouldFailValidationWhenCurrencyIsNull() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("100.00"));
            moneyDTO.setCurrency(null);

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Null currency should cause validation failure");
            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Money currency is required")),
                    "Should contain 'Money currency is required' message");
        }
    }

    @Nested
    @DisplayName("Multiple validation failures")
    class MultipleValidationFailures {

        @Test
        @DisplayName("Should fail validation with multiple errors when both fields are invalid")
        void shouldFailValidationWithMultipleErrorsWhenBothFieldsAreInvalid() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(null);
            moneyDTO.setCurrency(null);

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertEquals(2, violations.size(), "Should have exactly 2 validation errors");

            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Money amount is required")),
                    "Should contain amount validation message");

            assertTrue(violations.stream()
                            .anyMatch(v -> v.getMessage().equals("Money currency is required")),
                    "Should contain currency validation message");
        }

        @Test
        @DisplayName("Should fail validation with multiple errors for invalid amount and currency")
        void shouldFailValidationWithMultipleErrorsForInvalidAmountAndCurrency() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("-50.123"));
            moneyDTO.setCurrency(null);

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Should have validation errors");
            assertEquals(3, violations.size(), "Should have exactly 3 validation errors");

            // Verify all expected error messages are present
            Set<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(java.util.stream.Collectors.toSet());

            assertTrue(errorMessages.contains("must be greater than or equal to 0.01"));
            assertTrue(errorMessages.contains("Amount can have at most 2 decimal places"));
            assertTrue(errorMessages.contains("Money currency is required"));
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("Should pass validation with valid currency containing numbers")
        void shouldPassValidationWithValidCurrencyContainingNumbers() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("100.00"));
            moneyDTO.setCurrency("USD123");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Currency with numbers should be valid");
        }

        @Test
        @DisplayName("Should pass validation with very small valid amount")
        void shouldPassValidationWithVerySmallValidAmount() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("0.01"));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertTrue(violations.isEmpty(), "Minimum valid amount should pass validation");
        }

        @Test
        @DisplayName("Should fail validation with amount just below minimum")
        void shouldFailValidationWithAmountJustBelowMinimum() {
            // Given
            MoneyDTO moneyDTO = new MoneyDTO();
            moneyDTO.setAmount(new BigDecimal("0.009"));
            moneyDTO.setCurrency("EUR");

            // When
            Set<ConstraintViolation<MoneyDTO>> violations = validator.validate(moneyDTO);

            // Then
            assertFalse(violations.isEmpty(), "Amount below minimum should cause validation failure");
        }
    }
}