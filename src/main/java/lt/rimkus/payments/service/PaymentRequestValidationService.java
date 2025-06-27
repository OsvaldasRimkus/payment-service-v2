package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;

public interface PaymentRequestValidationService {
    boolean isPaymentCreationRequestValid(CreatePaymentRequestDTO requestDTO);
}
