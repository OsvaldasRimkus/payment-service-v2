package lt.rimkus.payments.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TYPE3PaymentTest {

    @Test
    @DisplayName("Should set and get Creditor Bank BIC")
    void shouldSetAndGetCreditorBankBIC() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        String bic = "DEUTDEFFXXX";

        // When
        payment.setCreditorBankBIC(bic);

        // Then
        assertEquals(bic, payment.getCreditorBankBIC());
    }

    @Test
    @DisplayName("Should set and get type from Payment base class")
    void shouldSetAndGetType() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        String type = "TYPE3";

        // When
        payment.setType(type);

        // Then
        assertEquals(type, payment.getType());
    }

    @Test
    @DisplayName("Should set and get money from Payment base class")
    void shouldSetAndGetMoney() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        Money money = new Money();
        money.setAmount(java.math.BigDecimal.valueOf(200.50));
        money.setCurrency("EUR");

        // When
        payment.setMoney(money);

        // Then
        assertEquals(money, payment.getMoney());
    }

    @Test
    @DisplayName("Should set and get debtor IBAN from Payment base class")
    void shouldSetAndGetDebtorIban() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        String iban = "DE89370400440532013000";

        // When
        payment.setDebtor_iban(iban);

        // Then
        assertEquals(iban, payment.getDebtor_iban());
    }

    @Test
    @DisplayName("Should set and get creditor IBAN from Payment base class")
    void shouldSetAndGetCreditorIban() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        String iban = "FR7630006000011234567890189";

        // When
        payment.setCreditor_iban(iban);

        // Then
        assertEquals(iban, payment.getCreditor_iban());
    }

    @Test
    @DisplayName("Should set and get created date and timestamp from Payment base class")
    void shouldSetAndGetCreatedDateTime() {
        // Given
        TYPE3Payment payment = new TYPE3Payment();
        var createdDate = java.time.LocalDate.now();
        var createdAt = java.time.LocalDateTime.now();

        // When
        payment.setCreatedDate(createdDate);
        payment.setCreatedAt(createdAt);

        // Then
        assertEquals(createdDate, payment.getCreatedDate());
        assertEquals(createdAt, payment.getCreatedAt());
    }
}