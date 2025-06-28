package lt.rimkus.payments.service;

import lt.rimkus.payments.model.Payment;

import java.util.concurrent.CompletableFuture;

public interface NotificationProcessor {
    CompletableFuture<String> notifyServiceAboutCreatedPayment(Payment payment);
}
