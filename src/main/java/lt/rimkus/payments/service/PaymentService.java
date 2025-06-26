package lt.rimkus.payments.service;

import lt.rimkus.payments.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment save(Payment newPayment);
}
