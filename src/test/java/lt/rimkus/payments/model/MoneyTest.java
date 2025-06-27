package lt.rimkus.payments.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    @DisplayName("Should create Money object using constructor with amount and currency")
    void shouldCreateMoneyUsingConstructor() {
        // Given
        BigDecimal amount = new BigDecimal("100.50");
        String currency = "EUR";

        // When
        Money money = new Money(amount, currency);

        // Then
        assertEquals(amount, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

    @Test
    @DisplayName("Should set and get amount correctly")
    void shouldSetAndGetAmount() {
        // Given
        Money money = new Money();
        BigDecimal amount = new BigDecimal("200.00");

        // When
        money.setAmount(amount);

        // Then
        assertEquals(amount, money.getAmount());
    }

    @Test
    @DisplayName("Should set and get currency correctly and trim spaces")
    void shouldSetAndGetTrimmedCurrency() {
        // Given
        Money money = new Money();
        String currency = "  USD  ";

        // When
        money.setCurrency(currency);

        // Then
        assertEquals("USD", money.getCurrency());
    }

    @Test
    @DisplayName("Should throw AssertionError when setting null amount")
    void shouldThrowAssertionErrorForNullAmount() {
        // Given
        Money money = new Money();

        // When / Then
        assertThrows(AssertionError.class, () -> money.setAmount(null));
    }

    @Test
    @DisplayName("Should throw AssertionError when setting null currency")
    void shouldThrowAssertionErrorForNullCurrency() {
        // Given
        Money money = new Money();

        // When / Then
        assertThrows(AssertionError.class, () -> money.setCurrency(null));
    }
}