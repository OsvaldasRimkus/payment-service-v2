package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import lt.rimkus.payments.service.impl.PaymentCancellationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentCancellationServiceImplTest {

    private final PaymentCancellationServiceImpl cancellationService = new PaymentCancellationServiceImpl();

    @Test
    @DisplayName("Should return false and set response message when payment is already cancelled")
    void shouldReturnFalseIfPaymentIsAlreadyCancelled() {
        // Given
        Payment payment = new TYPE1Payment();
        payment.setId(1L);
        payment.setCancelled(true);

        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();

        // When
        boolean result = cancellationService.isPaymentPreparedForCancellation(
                payment, LocalDate.now(), LocalDateTime.now(), responseDTO
        );

        // Then
        assertFalse(result);
        assertTrue(responseDTO.getMessage().contains("is already canceled"));
    }

    @Test
    @DisplayName("Should return false and set response message when cancellation date is not same as creation date")
    void shouldReturnFalseIfCancellationIsNotSameDay() {
        // Given
        Payment payment = new TYPE2Payment();
        payment.setId(2L);
        payment.setCreatedDate(LocalDate.now().minusDays(1));
        payment.setCancelled(false);

        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();

        // When
        boolean result = cancellationService.isPaymentPreparedForCancellation(
                payment, LocalDate.now(), LocalDateTime.now(), responseDTO
        );

        // Then
        assertFalse(result);
        assertEquals("Payment can be cancelled only on the day of its creation", responseDTO.getMessage());
    }

    @Test
    @DisplayName("Should return true and update payment when cancellation is valid")
    void shouldCancelPaymentSuccessfully() {
        // Given
        LocalDateTime creationTime = LocalDateTime.now().minusHours(3);
        LocalDateTime cancelTime = LocalDateTime.now();

        Payment payment = new TYPE3Payment();
        payment.setId(3L);
        payment.setCreatedDate(LocalDate.now());
        payment.setCreatedAt(creationTime);
        payment.setType("TYPE3");
        payment.setCancelled(false);

        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();

        // When
        boolean result = cancellationService.isPaymentPreparedForCancellation(
                payment, LocalDate.now(), cancelTime, responseDTO
        );

        // Then
        assertTrue(result);
        assertTrue(payment.isCancelled());
        assertNotNull(payment.getCancellationFee());
        assertEquals("EUR", payment.getCancellationFee().getCurrency());
        assertEquals(cancelTime, payment.getCancellationTime());
    }

    @Test
    @DisplayName("Should correctly calculate cancellation fee for TYPE1 payment")
    void shouldCalculateCancellationFeeCorrectly() {
        // Given
        String paymentType = "TYPE1";
        long hours = 4;

        // When
        BigDecimal fee = cancellationService.calculateCancellationFee(paymentType, hours);

        // Then
        assertEquals(new BigDecimal("0.20"), fee); // 4 * 0.05
    }

    @Test
    @DisplayName("Should update payment fields correctly on cancellation")
    void shouldUpdatePaymentDataCorrectly() {
        // Given
        Payment payment = new TYPE1Payment();
        LocalDateTime cancellationTime = LocalDateTime.now();
        BigDecimal fee = new BigDecimal("1.23");

        // When
        cancellationService.updatePaymentData(payment, cancellationTime, fee);

        // Then
        assertTrue(payment.isCancelled());
        assertEquals("EUR", payment.getCancellationFee().getCurrency());
        assertEquals(fee, payment.getCancellationFee().getAmount());
        assertEquals(cancellationTime, payment.getCancellationTime());
    }
}