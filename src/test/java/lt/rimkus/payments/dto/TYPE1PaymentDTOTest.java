package lt.rimkus.payments.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TYPE1PaymentDTOTest {

    @Test
    @DisplayName("Should set and get details correctly")
    void testSetAndGetDetails() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        String expectedDetails = "Payment for invoice #123";

        // When
        dto.setDetails(expectedDetails);

        // Then
        assertEquals(expectedDetails, dto.getDetails());
    }

    @Test
    @DisplayName("Should set and get inherited ID field correctly")
    void testSetAndGetId() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        Long expectedId = 42L;

        // When
        dto.setId(expectedId);

        // Then
        assertEquals(expectedId, dto.getId());
    }

    @Test
    @DisplayName("Should set and get inherited type field correctly")
    void testSetAndGetType() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        String expectedType = "TYPE1";

        // When
        dto.setType(expectedType);

        // Then
        assertEquals(expectedType, dto.getType());
    }

    @Test
    @DisplayName("Should set and get inherited money field correctly")
    void testSetAndGetMoney() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        MoneyDTO money = new MoneyDTO();
        money.setAmount(BigDecimal.valueOf(100.00));
        money.setCurrency("USD");

        // When
        dto.setMoney(money);

        // Then
        assertEquals(money, dto.getMoney());
        assertEquals(BigDecimal.valueOf(100.00), dto.getMoney().getAmount());
        assertEquals("USD", dto.getMoney().getCurrency());
    }

    @Test
    @DisplayName("Should set and get inherited debtor IBAN correctly")
    void testSetAndGetDebtorIban() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        String expectedIban = "DE001234567890";

        // When
        dto.setDebtor_iban(expectedIban);

        // Then
        assertEquals(expectedIban, dto.getDebtor_iban());
    }

    @Test
    @DisplayName("Should set and get inherited creditor IBAN correctly")
    void testSetAndGetCreditorIban() {
        // Given
        TYPE1PaymentDTO dto = new TYPE1PaymentDTO();
        String expectedIban = "FR009876543210";

        // When
        dto.setCreditor_iban(expectedIban);

        // Then
        assertEquals(expectedIban, dto.getCreditor_iban());
    }
}