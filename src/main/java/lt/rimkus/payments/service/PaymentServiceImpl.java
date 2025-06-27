package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.factory.PaymentCreationFactory;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentCreationFactory paymentCreationFactory;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentCreationFactory paymentCreationFactory) {
        this.paymentRepository = paymentRepository;
        this.paymentCreationFactory = paymentCreationFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional
    public CreatePaymentResponseDTO savePayment(CreatePaymentRequestDTO requestDTO, CreatePaymentResponseDTO responseDTO) {
        Payment newPayment = paymentCreationFactory.createNewPayment(requestDTO);
        paymentRepository.save(newPayment);
        return responseDTO;
    }
}
