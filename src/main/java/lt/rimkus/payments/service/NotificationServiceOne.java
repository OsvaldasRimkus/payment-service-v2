package lt.rimkus.payments.service;

import java.util.concurrent.CompletableFuture;

public interface NotificationServiceOne {
    CompletableFuture<String> notifyServiceAsync(String userName);
}
