package lt.rimkus.payments.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTypeTest {

    @Test
    @DisplayName("Should return true for valid payment type TYPE1")
    void shouldReturnTrueForValidType_TYPE1() {
        // Given
        String type = "TYPE1";

        // When
        boolean isValid = PaymentType.isValidType(type);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return true for valid payment type TYPE2")
    void shouldReturnTrueForValidType_TYPE2() {
        // Given
        String type = "TYPE2";

        // When
        boolean isValid = PaymentType.isValidType(type);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return true for valid payment type TYPE3")
    void shouldReturnTrueForValidType_TYPE3() {
        // Given
        String type = "TYPE3";

        // When
        boolean isValid = PaymentType.isValidType(type);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return false for invalid payment type UNKNOWN")
    void shouldReturnFalseForInvalidType_UNKNOWN() {
        // Given
        String type = "UNKNOWN";

        // When
        boolean isValid = PaymentType.isValidType(type);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should resolve TYPE1 payment type from code")
    void shouldResolve_TYPE1_FromCode() {
        // Given
        String code = "TYPE1";

        // When
        PaymentType type = PaymentType.resolvePaymentTypeFromCode(code);

        // Then
        assertEquals(PaymentType.TYPE1, type);
        assertEquals("TYPE1", type.getCode());
    }

    @Test
    @DisplayName("Should resolve TYPE2 payment type from code")
    void shouldResolve_TYPE2_FromCode() {
        // Given
        String code = "TYPE2";

        // When
        PaymentType type = PaymentType.resolvePaymentTypeFromCode(code);

        // Then
        assertEquals(PaymentType.TYPE2, type);
        assertEquals("TYPE2", type.getCode());
    }

    @Test
    @DisplayName("Should resolve TYPE3 payment type from code")
    void shouldResolve_TYPE3_FromCode() {
        // Given
        String code = "TYPE3";

        // When
        PaymentType type = PaymentType.resolvePaymentTypeFromCode(code);

        // Then
        assertEquals(PaymentType.TYPE3, type);
        assertEquals("TYPE3", type.getCode());
    }

    @Test
    @DisplayName("Should return null when resolving unknown payment type")
    void shouldReturnNullForInvalidCode() {
        // Given
        String code = "TYPE99";

        // When
        PaymentType type = PaymentType.resolvePaymentTypeFromCode(code);

        // Then
        assertNull(type);
    }

    @Test
    @DisplayName("Should return correct code using getCode()")
    void shouldReturnCorrectCode() {
        // Given & When
        String code1 = PaymentType.TYPE1.getCode();
        String code2 = PaymentType.TYPE2.getCode();
        String code3 = PaymentType.TYPE3.getCode();

        // Then
        assertEquals("TYPE1", code1);
        assertEquals("TYPE2", code2);
        assertEquals("TYPE3", code3);
    }
}