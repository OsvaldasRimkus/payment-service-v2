package lt.rimkus.payments.dto;

import lt.rimkus.payments.model.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CancelPaymentResponseDTOTest {

    @Test
    @DisplayName("Should initialize with empty validationErrors list")
    void shouldInitializeWithEmptyValidationErrors() {
        // Given & When
        CancelPaymentResponseDTO dto = new CancelPaymentResponseDTO();

        // Then
        assertNotNull(dto.getValidationErrors());
        assertTrue(dto.getValidationErrors().isEmpty());
    }

    @Test
    @DisplayName("Should set and get message correctly")
    void shouldSetAndGetMessage() {
        // Given
        CancelPaymentResponseDTO dto = new CancelPaymentResponseDTO();
        String message = "Payment cancelled successfully";

        // When
        dto.setMessage(message);

        // Then
        assertEquals(message, dto.getMessage());
    }

    @Test
    @DisplayName("Should set and get PaymentDTO correctly")
    void shouldSetAndGetPaymentDTO() {
        // Given
        CancelPaymentResponseDTO dto = new CancelPaymentResponseDTO();
        TYPE1PaymentDTO paymentDTO = new TYPE1PaymentDTO();
        paymentDTO.setDetails("Test payment");

        // When
        dto.setPaymentDTO(paymentDTO);

        // Then
        assertEquals(paymentDTO, dto.getPaymentDTO());
        assertEquals("Test payment", ((TYPE1PaymentDTO) dto.getPaymentDTO()).getDetails());
    }

    @Test
    @DisplayName("Should set and get cancellation fee correctly")
    void shouldSetAndGetCancellationFee() {
        // Given
        CancelPaymentResponseDTO dto = new CancelPaymentResponseDTO();
        Money fee = new Money(new BigDecimal("2.50"), "EUR");

        // When
        dto.setCancellationFee(fee);

        // Then
        assertEquals(fee, dto.getCancellationFee());
        assertEquals("EUR", dto.getCancellationFee().getCurrency());
        assertEquals(new BigDecimal("2.50"), dto.getCancellationFee().getAmount());
    }

    @Test
    @DisplayName("Should allow adding validation errors to list")
    void shouldAllowAddingValidationErrors() {
        // Given
        CancelPaymentResponseDTO dto = new CancelPaymentResponseDTO();

        // When
        dto.getValidationErrors().add("Error 1");
        dto.getValidationErrors().add("Error 2");

        // Then
        List<String> errors = dto.getValidationErrors();
        assertEquals(2, errors.size());
        assertTrue(errors.contains("Error 1"));
        assertTrue(errors.contains("Error 2"));
    }
}