package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestValidationServiceImpl implements PaymentRequestValidationService{
    @Override
    public boolean isPaymentCreationRequestValid(CreatePaymentRequestDTO requestDTO) {

        return false;
    }
}
