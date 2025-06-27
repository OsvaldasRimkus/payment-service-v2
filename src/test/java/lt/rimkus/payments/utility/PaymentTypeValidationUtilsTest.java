package lt.rimkus.payments.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentTypeValidationUtilsTest {

    @Test
    @DisplayName("Should return false for valid payment type code TYPE1")
    void shouldReturnFalseForValidPaymentTypeCode_TYPE1() {
        // Given
        String paymentTypeCode = "TYPE1";

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertFalse(result, "TYPE1 should be a valid payment type");
    }

    @Test
    @DisplayName("Should return false for valid payment type code TYPE2")
    void shouldReturnFalseForValidPaymentTypeCode_TYPE2() {
        // Given
        String paymentTypeCode = "TYPE2";

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertFalse(result, "TYPE2 should be a valid payment type");
    }

    @Test
    @DisplayName("Should return false for valid payment type code TYPE3")
    void shouldReturnFalseForValidPaymentTypeCode_TYPE3() {
        // Given
        String paymentTypeCode = "TYPE3";

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertFalse(result, "TYPE3 should be a valid payment type");
    }

    @Test
    @DisplayName("Should return true for invalid payment type code")
    void shouldReturnTrueForInvalidPaymentTypeCode() {
        // Given
        String paymentTypeCode = "INVALID_TYPE";

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertTrue(result, "INVALID_TYPE should be considered invalid");
    }

    @Test
    @DisplayName("Should return true for null payment type code")
    void shouldReturnTrueForNullPaymentTypeCode() {
        // Given
        String paymentTypeCode = null;

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertTrue(result, "Null should be considered invalid for payment type");
    }

    @Test
    @DisplayName("Should return true for empty payment type code")
    void shouldReturnTrueForEmptyPaymentTypeCode() {
        // Given
        String paymentTypeCode = "";

        // When
        boolean result = PaymentTypeValidationUtils.isPaymentTypeNotValid(paymentTypeCode);

        // Then
        assertTrue(result, "Empty string should be considered invalid for payment type");
    }
}