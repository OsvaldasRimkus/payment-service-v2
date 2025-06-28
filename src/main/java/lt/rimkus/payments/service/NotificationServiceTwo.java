package lt.rimkus.payments.service;

import java.util.concurrent.CompletableFuture;

public interface NotificationServiceTwo {
    CompletableFuture<String> notifyServiceAsync(String userName);
}
