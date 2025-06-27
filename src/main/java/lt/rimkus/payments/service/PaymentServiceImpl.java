package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentRequestValidationService paymentRequestValidationService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentRequestValidationService paymentRequestValidationService) {
        this.paymentRepository = paymentRepository;
        this.paymentRequestValidationService = paymentRequestValidationService;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public CreatePaymentResponseDTO save(CreatePaymentRequestDTO newPayment, CreatePaymentResponseDTO responseDTO) {
        if (paymentRequestValidationService.isPaymentCreationRequestValid(newPayment)) {

        } else {

        }

//        paymentRepository.save(newPayment);
        return responseDTO;
    }
}
