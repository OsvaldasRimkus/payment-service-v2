package lt.rimkus.payments.service.impl;

import lt.rimkus.payments.converter.PaymentConverter;
import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.dto.GetNotCancelledPaymentsDTO;
import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.factory.PaymentCreationFactory;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.repository.PaymentRepository;
import lt.rimkus.payments.service.NotificationProcessor;
import lt.rimkus.payments.service.PaymentCancellationService;
import lt.rimkus.payments.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static lt.rimkus.payments.message.ResponseMessages.FAILURE;
import static lt.rimkus.payments.message.ResponseMessages.PAYMENT_DOES_NOT_EXIST;
import static lt.rimkus.payments.message.ResponseMessages.SUCCESS;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentCreationFactory paymentCreationFactory;
    private final PaymentConverter paymentConverter;
    private final PaymentCancellationService paymentCancellationService;
    private final NotificationProcessor notificationProcessor;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentCreationFactory paymentCreationFactory, PaymentConverter paymentConverter, PaymentCancellationService paymentCancellationService, NotificationProcessor notificationProcessor) {
        this.paymentRepository = paymentRepository;
        this.paymentCreationFactory = paymentCreationFactory;
        this.paymentConverter = paymentConverter;
        this.paymentCancellationService = paymentCancellationService;
        this.notificationProcessor = notificationProcessor;
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
        notifyServiceAndUpdatePaymentInDatabase(newPayment);
        responseDTO.setPaymentDTO(paymentConverter.convertPaymentToDTO(newPayment));
        return responseDTO;
    }

    private void notifyServiceAndUpdatePaymentInDatabase(Payment newPayment) {
        CompletableFuture<String> notificationResult = notificationProcessor.notifyServiceAboutCreatedPayment(newPayment);
        notificationResult.thenAccept((result) -> {
            newPayment.setNotificationStatus(result != null && result.equals(SUCCESS) ? SUCCESS : FAILURE);
            // Updating payment with notification status in DB
            paymentRepository.save(newPayment);
        });
    }

    @Override
    @Transactional
    public CancelPaymentResponseDTO cancelPayment(Long paymentId, CancelPaymentResponseDTO responseDTO) {
        LocalDate dateOfCancellationRequest = LocalDate.now();
        LocalDateTime timeOfCancellationRequest = LocalDateTime.now();

        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isEmpty()) {
            responseDTO.setMessage(PAYMENT_DOES_NOT_EXIST);
            return responseDTO;
        } else {
            Payment paymentToCancel = payment.get();
            if (paymentCancellationService.isPaymentPreparedForCancellation(paymentToCancel, dateOfCancellationRequest, timeOfCancellationRequest, responseDTO)) {
                paymentRepository.save(paymentToCancel);
                responseDTO.setPaymentDTO(paymentConverter.convertPaymentToDTO(paymentToCancel));
                responseDTO.setCancellationFee(new Money(paymentToCancel.getCancellationFee().getAmount(), paymentToCancel.getCancellationFee().getCurrency()));
            }
        }
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getNotCanceledPaymentIds(GetNotCancelledPaymentsDTO requestDTO) {
        BigDecimal minAmount = requestDTO.getMinAmount();
        BigDecimal maxAmount = requestDTO.getMaxAmount();
        if (!requestDTO.isFilter()) {
            minAmount = null;
            maxAmount = null;
        }
        return paymentRepository.getNotCancelledPaymentsWithinRange(minAmount, maxAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentCancellationInfoDTO getPaymentCancellationDetails(Long id) {
        return paymentRepository.getPaymentCancellationDetails(id);
    }

}
