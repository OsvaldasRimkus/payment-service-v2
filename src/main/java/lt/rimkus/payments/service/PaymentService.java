package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    CreatePaymentResponseDTO save(CreatePaymentRequestDTO newPayment, CreatePaymentResponseDTO responseDTO);
}
