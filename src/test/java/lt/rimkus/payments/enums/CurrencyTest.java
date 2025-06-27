package lt.rimkus.payments.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyTest {

    @Test
    @DisplayName("Should return true for valid currency code EUR")
    void shouldReturnTrueForValidCurrencyEUR() {
        // Given
        String code = "EUR";

        // When
        boolean isValid = Currency.isValidCurrency(code);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return true for valid currency code USD")
    void shouldReturnTrueForValidCurrencyUSD() {
        // Given
        String code = "USD";

        // When
        boolean isValid = Currency.isValidCurrency(code);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return false for invalid currency code GBP")
    void shouldReturnFalseForInvalidCurrencyGBP() {
        // Given
        String code = "GBP";

        // When
        boolean isValid = Currency.isValidCurrency(code);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should resolve EUR currency from code")
    void shouldResolveEURFromCode() {
        // Given
        String code = "EUR";

        // When
        Currency currency = Currency.resolveCurrencyFromCode(code);

        // Then
        assertEquals(Currency.EUR, currency);
        assertEquals("EUR", currency.getCode());
    }

    @Test
    @DisplayName("Should resolve USD currency from code")
    void shouldResolveUSDFromCode() {
        // Given
        String code = "USD";

        // When
        Currency currency = Currency.resolveCurrencyFromCode(code);

        // Then
        assertEquals(Currency.USD, currency);
        assertEquals("USD", currency.getCode());
    }

    @Test
    @DisplayName("Should return null when resolving invalid currency code")
    void shouldReturnNullForInvalidCode() {
        // Given
        String code = "JPY";

        // When
        Currency currency = Currency.resolveCurrencyFromCode(code);

        // Then
        assertNull(currency);
    }

    @Test
    @DisplayName("Should return correct currency code from getCode()")
    void shouldReturnCorrectCurrencyCode() {
        // Given & When
        String eurCode = Currency.EUR.getCode();
        String usdCode = Currency.USD.getCode();

        // Then
        assertEquals("EUR", eurCode);
        assertEquals("USD", usdCode);
    }
}