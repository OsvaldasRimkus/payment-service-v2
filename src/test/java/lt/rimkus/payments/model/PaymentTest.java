package lt.rimkus.payments.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    static class TestPayment extends Payment {}

    @Test
    @DisplayName("Should set and get type")
    void shouldSetAndGetType() {
        // Given
        TestPayment payment = new TestPayment();
        String type = "TYPE1";

        // When
        payment.setType(type);

        // Then
        assertEquals(type, payment.getType());
    }

    @Test
    @DisplayName("Should set and get money")
    void shouldSetAndGetMoney() {
        // Given
        TestPayment payment = new TestPayment();
        Money money = new Money(BigDecimal.valueOf(123.45), "EUR");

        // When
        payment.setMoney(money);

        // Then
        assertEquals(money, payment.getMoney());
    }

    @Test
    @DisplayName("Should set and get debtor IBAN")
    void shouldSetAndGetDebtorIban() {
        // Given
        TestPayment payment = new TestPayment();
        String iban = "DE89370400440532013000";

        // When
        payment.setDebtor_iban(iban);

        // Then
        assertEquals(iban, payment.getDebtor_iban());
    }

    @Test
    @DisplayName("Should set and get creditor IBAN")
    void shouldSetAndGetCreditorIban() {
        // Given
        TestPayment payment = new TestPayment();
        String iban = "FR7630006000011234567890189";

        // When
        payment.setCreditor_iban(iban);

        // Then
        assertEquals(iban, payment.getCreditor_iban());
    }

    @Test
    @DisplayName("Should set and get created date")
    void shouldSetAndGetCreatedDate() {
        // Given
        TestPayment payment = new TestPayment();
        LocalDate date = LocalDate.of(2025, 6, 26);

        // When
        payment.setCreatedDate(date);

        // Then
        assertEquals(date, payment.getCreatedDate());
    }

    @Test
    @DisplayName("Should set and get created timestamp")
    void shouldSetAndGetCreatedAt() {
        // Given
        TestPayment payment = new TestPayment();
        LocalDateTime timestamp = LocalDateTime.of(2025, 6, 26, 12, 30);

        // When
        payment.setCreatedAt(timestamp);

        // Then
        assertEquals(timestamp, payment.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get cancelled status")
    void shouldSetAndGetCancelled() {
        // Given
        TestPayment payment = new TestPayment();

        // When
        payment.setCancelled(true);

        // Then
        assertTrue(payment.isCancelled());
    }

    @Test
    @DisplayName("Should set and get cancellation fee")
    void shouldSetAndGetCancellationFee() {
        // Given
        TestPayment payment = new TestPayment();
        Money fee = new Money(BigDecimal.valueOf(5.50), "USD");

        // When
        payment.setCancellationFee(fee);

        // Then
        assertEquals(fee, payment.getCancellationFee());
    }

    @Test
    @DisplayName("Should set and get cancellation time")
    void shouldSetAndGetCancellationTime() {
        // Given
        TestPayment payment = new TestPayment();
        LocalDateTime time = LocalDateTime.now();

        // When
        payment.setCancellationTime(time);

        // Then
        assertEquals(time, payment.getCancellationTime());
    }

    @Test
    @DisplayName("Should set and get notification status")
    void shouldSetAndGetNotificationStatus() {
        // Given
        TestPayment payment = new TestPayment();
        String status = "SENT";

        // When
        payment.setNotificationStatus(status);

        // Then
        assertEquals(status, payment.getNotificationStatus());
    }
}