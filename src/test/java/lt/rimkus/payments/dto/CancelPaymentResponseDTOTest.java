package lt.rimkus.payments.dto;

import lt.rimkus.payments.model.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CancelPaymentResponseDTOTest {

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
}