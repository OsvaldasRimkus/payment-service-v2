package lt.rimkus.payments.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreatePaymentRequestDTO Validation Tests")
class CreatePaymentRequestDTOTest {

    private Validator validator;
    private CreatePaymentRequestDTO paymentRequest;
    private MoneyDTO validMoney;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Setup valid money object for tests
        validMoney = new MoneyDTO();
        validMoney.setAmount(new BigDecimal("100.00"));
        validMoney.setCurrency("USD");

        // Setup base valid payment request
        paymentRequest = new CreatePaymentRequestDTO();
        paymentRequest.setType("TYPE2");
        paymentRequest.setMoney(validMoney);
        paymentRequest.setDebtor_iban("DE89370400440532013000");
        paymentRequest.setCreditor_iban("FR1420041010050500013M02606");
    }

    @Nested
    @DisplayName("Payment Type Validation")
    class PaymentTypeValidation {

        @Test
        @DisplayName("Given valid payment type, When validating, Then validation should pass")
        void givenValidPaymentType_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setType("TYPE2");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given null payment type, When validating, Then should return validation error")
        void givenNullPaymentType_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setType(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Payment type missing", violation.getMessage());
            assertEquals("type", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given empty payment type, When validating, Then should return validation error")
        void givenEmptyPaymentType_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setType("");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Payment type missing", violation.getMessage());
            assertEquals("type", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given blank payment type, When validating, Then should return validation error")
        void givenBlankPaymentType_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setType("   ");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Payment type missing", violation.getMessage());
            assertEquals("type", violation.getPropertyPath().toString());
        }
    }

    @Nested
    @DisplayName("Money Validation")
    class MoneyValidation {

        @Test
        @DisplayName("Given valid money object, When validating, Then validation should pass")
        void givenValidMoneyObject_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setMoney(validMoney);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given null money object, When validating, Then should return validation error")
        void givenNullMoneyObject_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setMoney(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Money must not be null", violation.getMessage());
            assertEquals("money", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given money object with validation errors, When validating, Then should return nested validation errors")
        void givenMoneyObjectWithValidationErrors_whenValidating_thenShouldReturnNestedValidationErrors() {
            // Given
            MoneyDTO invalidMoney = new MoneyDTO();
            // Assuming MoneyDTO has validation annotations that will fail
            paymentRequest.setMoney(invalidMoney);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertFalse(violations.isEmpty());
            // Note: The exact violations depend on MoneyDTO's validation annotations
        }
    }

    @Nested
    @DisplayName("Debtor IBAN Validation")
    class DebtorIbanValidation {

        @Test
        @DisplayName("Given valid debtor IBAN, When validating, Then validation should pass")
        void givenValidDebtorIban_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setDebtor_iban("DE89370400440532013000");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given null debtor IBAN, When validating, Then should return validation error")
        void givenNullDebtorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setDebtor_iban(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Debtor IBAN missing", violation.getMessage());
            assertEquals("debtor_iban", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given empty debtor IBAN, When validating, Then should return validation error")
        void givenEmptyDebtorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setDebtor_iban("");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Debtor IBAN missing", violation.getMessage());
            assertEquals("debtor_iban", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given blank debtor IBAN, When validating, Then should return validation error")
        void givenBlankDebtorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setDebtor_iban("   ");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Debtor IBAN missing", violation.getMessage());
            assertEquals("debtor_iban", violation.getPropertyPath().toString());
        }
    }

    @Nested
    @DisplayName("Creditor IBAN Validation")
    class CreditorIbanValidation {

        @Test
        @DisplayName("Given valid creditor IBAN, When validating, Then validation should pass")
        void givenValidCreditorIban_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setCreditor_iban("FR1420041010050500013M02606");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given null creditor IBAN, When validating, Then should return validation error")
        void givenNullCreditorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setCreditor_iban(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Creditor IBAN missing", violation.getMessage());
            assertEquals("creditor_iban", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given empty creditor IBAN, When validating, Then should return validation error")
        void givenEmptyCreditorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setCreditor_iban("");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Creditor IBAN missing", violation.getMessage());
            assertEquals("creditor_iban", violation.getPropertyPath().toString());
        }

        @Test
        @DisplayName("Given blank creditor IBAN, When validating, Then should return validation error")
        void givenBlankCreditorIban_whenValidating_thenShouldReturnValidationError() {
            // Given
            paymentRequest.setCreditor_iban("   ");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(1, violations.size());
            ConstraintViolation<CreatePaymentRequestDTO> violation = violations.iterator().next();
            assertEquals("Creditor IBAN missing", violation.getMessage());
            assertEquals("creditor_iban", violation.getPropertyPath().toString());
        }
    }

    @Nested
    @DisplayName("Optional Fields Validation")
    class OptionalFieldsValidation {

        @Test
        @DisplayName("Given null creditor bank BIC, When validating, Then validation should pass")
        void givenNullCreditorBankBic_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setCreditorBankBIC(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given valid creditor bank BIC, When validating, Then validation should pass")
        void givenValidCreditorBankBic_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setCreditorBankBIC("DEUTDEFF");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given null details, When validating, Then validation should pass")
        void givenNullDetails_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setDetails(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given valid details, When validating, Then validation should pass")
        void givenValidDetails_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setDetails("Payment for invoice #12345");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("Multiple Field Validation Errors")
    class MultipleFieldValidationErrors {

        @Test
        @DisplayName("Given multiple null required fields, When validating, Then should return multiple validation errors")
        void givenMultipleNullRequiredFields_whenValidating_thenShouldReturnMultipleValidationErrors() {
            // Given
            paymentRequest.setType(null);
            paymentRequest.setMoney(null);
            paymentRequest.setDebtor_iban(null);
            paymentRequest.setCreditor_iban(null);

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(4, violations.size());
        }

        @Test
        @DisplayName("Given multiple blank required fields, When validating, Then should return multiple validation errors")
        void givenMultipleBlankRequiredFields_whenValidating_thenShouldReturnMultipleValidationErrors() {
            // Given
            paymentRequest.setType("");
            paymentRequest.setDebtor_iban("   ");
            paymentRequest.setCreditor_iban("");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertEquals(3, violations.size());
        }
    }

    @Nested
    @DisplayName("Complete Valid Object")
    class CompleteValidObject {

        @Test
        @DisplayName("Given complete valid payment request, When validating, Then validation should pass")
        void givenCompleteValidPaymentRequest_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setType("TYPE3");
            paymentRequest.setMoney(validMoney);
            paymentRequest.setDebtor_iban("DE89370400440532013000");
            paymentRequest.setCreditor_iban("FR1420041010050500013M02606");
            paymentRequest.setCreditorBankBIC("DEUTDEFF");
            paymentRequest.setDetails("Payment for services");

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Given minimal valid payment request, When validating, Then validation should pass")
        void givenMinimalValidPaymentRequest_whenValidating_thenValidationShouldPass() {
            // Given
            paymentRequest.setType("TYPE2");
            paymentRequest.setMoney(validMoney);
            paymentRequest.setDebtor_iban("DE89370400440532013000");
            paymentRequest.setCreditor_iban("FR1420041010050500013M02606");
            // Optional fields left as null

            // When
            Set<ConstraintViolation<CreatePaymentRequestDTO>> violations = validator.validate(paymentRequest);

            // Then
            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterAndSetterTests {

        @Test
        @DisplayName("Given payment request, When setting and getting type, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingType_thenShouldReturnCorrectValue() {
            // Given
            String expectedType = "TYPE2";

            // When
            paymentRequest.setType(expectedType);
            String actualType = paymentRequest.getType();

            // Then
            assertEquals(expectedType, actualType);
        }

        @Test
        @DisplayName("Given payment request, When setting and getting money, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingMoney_thenShouldReturnCorrectValue() {
            // Given
            MoneyDTO expectedMoney = new MoneyDTO();

            // When
            paymentRequest.setMoney(expectedMoney);
            MoneyDTO actualMoney = paymentRequest.getMoney();

            // Then
            assertEquals(expectedMoney, actualMoney);
        }

        @Test
        @DisplayName("Given payment request, When setting and getting debtor IBAN, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingDebtorIban_thenShouldReturnCorrectValue() {
            // Given
            String expectedIban = "GB82WEST12345698765432";

            // When
            paymentRequest.setDebtor_iban(expectedIban);
            String actualIban = paymentRequest.getDebtor_iban();

            // Then
            assertEquals(expectedIban, actualIban);
        }

        @Test
        @DisplayName("Given payment request, When setting and getting creditor IBAN, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingCreditorIban_thenShouldReturnCorrectValue() {
            // Given
            String expectedIban = "ES9121000418450200051332";

            // When
            paymentRequest.setCreditor_iban(expectedIban);
            String actualIban = paymentRequest.getCreditor_iban();

            // Then
            assertEquals(expectedIban, actualIban);
        }

        @Test
        @DisplayName("Given payment request, When setting and getting creditor bank BIC, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingCreditorBankBic_thenShouldReturnCorrectValue() {
            // Given
            String expectedBic = "SWIFT123";

            // When
            paymentRequest.setCreditorBankBIC(expectedBic);
            String actualBic = paymentRequest.getCreditorBankBIC();

            // Then
            assertEquals(expectedBic, actualBic);
        }

        @Test
        @DisplayName("Given payment request, When setting and getting details, Then should return correct value")
        void givenPaymentRequest_whenSettingAndGettingDetails_thenShouldReturnCorrectValue() {
            // Given
            String expectedDetails = "Monthly subscription payment";

            // When
            paymentRequest.setDetails(expectedDetails);
            String actualDetails = paymentRequest.getDetails();

            // Then
            assertEquals(expectedDetails, actualDetails);
        }
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Given default constructor, When creating payment request, Then all fields should be null")
        void givenDefaultConstructor_whenCreatingPaymentRequest_thenAllFieldsShouldBeNull() {
            // Given & When
            CreatePaymentRequestDTO newPaymentRequest = new CreatePaymentRequestDTO();

            // Then
            assertNull(newPaymentRequest.getType());
            assertNull(newPaymentRequest.getMoney());
            assertNull(newPaymentRequest.getDebtor_iban());
            assertNull(newPaymentRequest.getCreditor_iban());
            assertNull(newPaymentRequest.getCreditorBankBIC());
            assertNull(newPaymentRequest.getDetails());
        }
    }
}