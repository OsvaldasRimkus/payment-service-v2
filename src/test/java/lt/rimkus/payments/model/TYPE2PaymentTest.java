package lt.rimkus.payments.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TYPE2PaymentTest {

    @Test
    @DisplayName("Should set and get payment details")
    void shouldSetAndGetDetails() {
        // Given
        TYPE2Payment payment = new TYPE2Payment();
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
        TYPE2Payment payment = new TYPE2Payment();
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
        TYPE2Payment payment = new TYPE2Payment();
        Money money = new Money();

        // When
        payment.setMoney(money);

        // Then
        assertEquals(money, payment.getMoney());
    }
}