package lt.rimkus.payments.converter;

import lt.rimkus.payments.dto.PaymentDTO;
import lt.rimkus.payments.model.Payment;

public interface PaymentConverter {
    PaymentDTO convertPaymentToDTO(Payment payment);
}
