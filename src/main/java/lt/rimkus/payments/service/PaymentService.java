package lt.rimkus.payments.service;

import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.dto.GetNotCancelledPaymentsDTO;
import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    CreatePaymentResponseDTO savePayment(CreatePaymentRequestDTO newPayment, CreatePaymentResponseDTO responseDTO);

    CancelPaymentResponseDTO cancelPayment(Long paymentId, CancelPaymentResponseDTO responseDTO);

    List<Long> getNotCanceledPaymentIds(GetNotCancelledPaymentsDTO requestDTO);

    PaymentCancellationInfoDTO getPaymentCancellationDetails(Long paymentId);
}
