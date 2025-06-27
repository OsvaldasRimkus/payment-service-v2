package lt.rimkus.payments.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    static class TestPaymentDTO extends PaymentDTO {
        // concrete class for testing the abstract PaymentDTO
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void testSetAndGetId() {
        // Given
        TestPaymentDTO dto = new TestPaymentDTO();
        Long id = 100L;

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    @DisplayName("Should set and get type correctly")
    void testSetAndGetType() {
        // Given
        TestPaymentDTO dto = new TestPaymentDTO();
        String type = "TYPE1";

        // When
        dto.setType(type);

        // Then
        assertEquals(type, dto.getType());
    }

    @Test
    @DisplayName("Should set and get money correctly")
    void testSetAndGetMoney() {
        // Given
        TestPaymentDTO dto = new TestPaymentDTO();
        MoneyDTO money = new MoneyDTO();
        money.setAmount(BigDecimal.valueOf(20.00));
        money.setCurrency("EUR");

        // When
        dto.setMoney(money);

        // Then
        assertEquals(money, dto.getMoney());
        assertEquals(BigDecimal.valueOf(20.00), dto.getMoney().getAmount());
        assertEquals("EUR", dto.getMoney().getCurrency());
    }

    @Test
    @DisplayName("Should set and get debtor IBAN correctly")
    void testSetAndGetDebtorIban() {
        // Given
        TestPaymentDTO dto = new TestPaymentDTO();
        String iban = "DE1234567890";

        // When
        dto.setDebtor_iban(iban);

        // Then
        assertEquals(iban, dto.getDebtor_iban());
    }

    @Test
    @DisplayName("Should set and get creditor IBAN correctly")
    void testSetAndGetCreditorIban() {
        // Given
        TestPaymentDTO dto = new TestPaymentDTO();
        String iban = "FR0987654321";

        // When
        dto.setCreditor_iban(iban);

        // Then
        assertEquals(iban, dto.getCreditor_iban());
    }
}