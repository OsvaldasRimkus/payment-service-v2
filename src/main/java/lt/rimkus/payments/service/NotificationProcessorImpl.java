package lt.rimkus.payments.service;

import lt.rimkus.payments.model.Payment;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static lt.rimkus.payments.enums.PaymentType.TYPE1;
import static lt.rimkus.payments.enums.PaymentType.TYPE2;

@Service
public class NotificationProcessorImpl implements NotificationProcessor {
    private final NotificationServiceOne notificationServiceOne;
    private final NotificationServiceTwo notificationServiceTwo;

    public NotificationProcessorImpl(NotificationServiceOne notificationServiceOne, NotificationServiceTwo notificationServiceTwo) {
        this.notificationServiceOne = notificationServiceOne;
        this.notificationServiceTwo = notificationServiceTwo;
    }

    @Override
    public CompletableFuture<String> notifyServiceAboutCreatedPayment(Payment payment) {
        if (TYPE1.getCode().equals(payment.getType())) {
            return notificationServiceOne.notifyServiceAsync("osvaldasrimkus");
        }
        if (TYPE2.getCode().equals(payment.getType())) {
            return notificationServiceTwo.notifyServiceAsync("osvaldasrimkus");
        }
        return new CompletableFuture<>();
    }
}
