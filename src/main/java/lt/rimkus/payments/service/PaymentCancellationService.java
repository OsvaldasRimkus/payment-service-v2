package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.model.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PaymentCancellationService {
    boolean isPaymentPreparedForCancellation(Payment paymentToCancel, LocalDate dateOfCancellationRequest, LocalDateTime timeOfCancellationRequest, CancelPaymentResponseDTO responseDTO);
}
