package lt.rimkus.payments.factory;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.enums.PaymentType;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentCreationFactoryImpl implements PaymentCreationFactory {
    @Override
    public Payment createNewPayment(CreatePaymentRequestDTO requestDTO) {
        return null;
    }

    private Payment createPayment(CreatePaymentRequestDTO requestDTO) {
        PaymentType paymentType = PaymentType.valueOf(requestDTO.getType());
        return switch (paymentType) {
            case TYPE1 -> new TYPE1Payment();
            case TYPE2 -> new TYPE2Payment();
            case TYPE3 -> new TYPE3Payment();
        };
    }
}
