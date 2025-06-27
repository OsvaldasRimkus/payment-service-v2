package lt.rimkus.payments.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TYPE1PaymentTest {

    @Test
    @DisplayName("Should set and get payment details")
    void shouldSetAndGetDetails() {
        // Given
        TYPE1Payment payment = new TYPE1Payment();
        String expectedDetails = "Payment for invoice #123";

        // When
        payment.setDetails(expectedDetails);

        // Then
        assertEquals(expectedDetails, payment.getDetails());
    }

    @Test
    @DisplayName("Should inherit and use base class fields")
    void shouldInheritFieldsFromPayment() {
        // Given
        TYPE1Payment payment = new TYPE1Payment();
        String type = "TYPE1";

        // When
        payment.setType(type);

        // Then
        assertEquals(type, payment.getType());
    }

    @Test
    @DisplayName("Should allow setting money from base class")
    void shouldSetMoneyFromParentClass() {
        // Given
        TYPE1Payment payment = new TYPE1Payment();
        Money money = new Money();

        // When
        payment.setMoney(money);

        // Then
        assertEquals(money, payment.getMoney());
    }
}