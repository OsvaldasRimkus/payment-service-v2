package lt.rimkus.payments.factory;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.model.Payment;

public interface PaymentCreationFactory {
    Payment createNewPayment(CreatePaymentRequestDTO requestDTO);
}
