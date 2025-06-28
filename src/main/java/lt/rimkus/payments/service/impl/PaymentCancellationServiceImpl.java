package lt.rimkus.payments.service.impl;

import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.enums.Currency;
import lt.rimkus.payments.enums.PaymentType;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.service.PaymentCancellationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static lt.rimkus.payments.message.ResponseMessages.IS_ALREADY_CANCELED;
import static lt.rimkus.payments.message.ResponseMessages.PAYMENT_WITH_ID;
import static lt.rimkus.payments.message.ResponseMessages.SAME_DAY_CANCELLATION_ONLY;

@Component
public class PaymentCancellationServiceImpl implements PaymentCancellationService {

    private static final Map<String, BigDecimal> PAYMENT_TYPE_AND_CANCELLATION_COEFFICIENT_MAP =
            Map.of(
                    PaymentType.TYPE1.getCode(), BigDecimal.valueOf(0.05),
                    PaymentType.TYPE2.getCode(), BigDecimal.valueOf(0.1),
                    PaymentType.TYPE3.getCode(), BigDecimal.valueOf(0.15)
            );

    @Override
    public boolean isPaymentPreparedForCancellation(Payment paymentToCancel, LocalDate dateOfCancellationRequest, LocalDateTime timeOfCancellationRequest, CancelPaymentResponseDTO responseDTO) {
        if (paymentToCancel.isCancelled()) {
            responseDTO.setMessage(PAYMENT_WITH_ID + paymentToCancel.getId() + IS_ALREADY_CANCELED);
            return false;
        } else if (!dateOfCancellationRequest.isEqual(paymentToCancel.getCreatedDate())) {
            responseDTO.setMessage(SAME_DAY_CANCELLATION_ONLY);
            return false;
        }

        long numberOfHours = Duration.between(paymentToCancel.getCreatedAt(), timeOfCancellationRequest).getSeconds() / 3600;
        BigDecimal cancellationFee = calculateCancellationFee(paymentToCancel.getType(), numberOfHours);
        updatePaymentData(paymentToCancel, timeOfCancellationRequest, cancellationFee);
        return true;
    }

    public BigDecimal calculateCancellationFee(String paymentType, long hours) {
        BigDecimal coefficient = PAYMENT_TYPE_AND_CANCELLATION_COEFFICIENT_MAP.get(paymentType);
        return BigDecimal.valueOf(hours).multiply(coefficient).setScale(2, RoundingMode.CEILING);
    }

    public void updatePaymentData(Payment paymentToCancel, LocalDateTime timeOfCancellationRequest, BigDecimal cancellationFee) {
        paymentToCancel.setCancelled(true);
        paymentToCancel.setCancellationFee(new Money(cancellationFee, Currency.EUR.getCode()));
        paymentToCancel.setCancellationTime(timeOfCancellationRequest);
    }

}
