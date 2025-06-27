package lt.rimkus.payments.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TYPE3PaymentDTOTest {

    @Test
    @DisplayName("Should set and get creditorBankBIC correctly")
    void testSetAndGetCreditorBankBIC() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        String expectedBIC = "BANKDEFFXXX";

        // When
        dto.setCreditorBankBIC(expectedBIC);

        // Then
        assertEquals(expectedBIC, dto.getCreditorBankBIC());
    }

    @Test
    @DisplayName("Should set and get inherited ID field correctly")
    void testSetAndGetId() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        Long expectedId = 100L;

        // When
        dto.setId(expectedId);

        // Then
        assertEquals(expectedId, dto.getId());
    }

    @Test
    @DisplayName("Should set and get inherited type field correctly")
    void testSetAndGetType() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        String expectedType = "TYPE3";

        // When
        dto.setType(expectedType);

        // Then
        assertEquals(expectedType, dto.getType());
    }

    @Test
    @DisplayName("Should set and get inherited money field correctly")
    void testSetAndGetMoney() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        MoneyDTO money = new MoneyDTO();
        money.setAmount(BigDecimal.valueOf(500.00));
        money.setCurrency("EUR");

        // When
        dto.setMoney(money);

        // Then
        assertEquals(money, dto.getMoney());
        assertEquals(BigDecimal.valueOf(500.00), dto.getMoney().getAmount());
        assertEquals("EUR", dto.getMoney().getCurrency());
    }

    @Test
    @DisplayName("Should set and get inherited debtor IBAN correctly")
    void testSetAndGetDebtorIban() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        String expectedIban = "DE44500105175407324931";

        // When
        dto.setDebtor_iban(expectedIban);

        // Then
        assertEquals(expectedIban, dto.getDebtor_iban());
    }

    @Test
    @DisplayName("Should set and get inherited creditor IBAN correctly")
    void testSetAndGetCreditorIban() {
        // Given
        TYPE3PaymentDTO dto = new TYPE3PaymentDTO();
        String expectedIban = "FR7630006000011234567890189";

        // When
        dto.setCreditor_iban(expectedIban);

        // Then
        assertEquals(expectedIban, dto.getCreditor_iban());
    }
}