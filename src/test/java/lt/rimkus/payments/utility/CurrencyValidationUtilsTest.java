package lt.rimkus.payments.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyValidationUtilsTest {

    @Test
    @DisplayName("Should return false for a valid currency code")
    void shouldReturnFalseForValidCurrencyCode() {
        // Given
        String validCurrency = "EUR";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValid(validCurrency);

        // Then
        assertFalse(result, "EUR should be considered a valid currency");
    }

    @Test
    @DisplayName("Should return true for an invalid currency code")
    void shouldReturnTrueForInvalidCurrencyCode() {
        // Given
        String invalidCurrency = "GBP";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValid(invalidCurrency);

        // Then
        assertTrue(result, "GBP is not in the supported Currency enum");
    }

    @Test
    @DisplayName("Should return false when currency is valid for payment type")
    void shouldReturnFalseWhenCurrencyIsValidForPaymentType() {
        // Given
        String currency = "EUR";
        String paymentType = "TYPE1";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertFalse(result, "EUR should be valid for TYPE1");
    }

    @Test
    @DisplayName("Should return true when currency is not valid for payment type")
    void shouldReturnTrueWhenCurrencyNotValidForPaymentType() {
        // Given
        String currency = "USD";
        String paymentType = "TYPE1";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertTrue(result, "USD should not be valid for TYPE1");
    }

    @Test
    @DisplayName("Should return true when currency code is invalid")
    void shouldReturnTrueWhenCurrencyCodeIsInvalid() {
        // Given
        String currency = "XYZ";
        String paymentType = "TYPE3";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertTrue(result, "Invalid currency code should result in true");
    }

    @Test
    @DisplayName("Should return true when payment type code is invalid")
    void shouldReturnTrueWhenPaymentTypeCodeIsInvalid() {
        // Given
        String currency = "EUR";
        String paymentType = "UNKNOWN_TYPE";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertTrue(result, "Invalid payment type should result in true");
    }

    @Test
    @DisplayName("Should return false when EUR is valid for TYPE3")
    void shouldReturnFalseWhenEurValidForType3() {
        // Given
        String currency = "EUR";
        String paymentType = "TYPE3";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when USD is valid for TYPE3")
    void shouldReturnFalseWhenUsdValidForType3() {
        // Given
        String currency = "USD";
        String paymentType = "TYPE3";

        // When
        boolean result = CurrencyValidationUtils.isCurrencyNotValidForPaymentType(currency, paymentType);

        // Then
        assertFalse(result);
    }
}