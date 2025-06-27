package lt.rimkus.payments.factory;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.enums.PaymentType;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PaymentCreationFactoryImpl implements PaymentCreationFactory {

    @Override
    public Payment createNewPayment(CreatePaymentRequestDTO requestDTO) {
        PaymentType paymentType = PaymentType.valueOf(requestDTO.getType());
        Payment payment = switch (paymentType) {
            case TYPE1 -> createTYPE1Payment_populateSpecificData(requestDTO);
            case TYPE2 -> createTYPE2Payment_populateSpecificData(requestDTO);
            case TYPE3 -> createTYPE3Payment_populateSpecificData(requestDTO);
        };
        populateCommonData(payment, requestDTO);
        return payment;
    }

    private Payment createTYPE1Payment_populateSpecificData(CreatePaymentRequestDTO requestDTO) {
        TYPE1Payment payment = new TYPE1Payment();
        payment.setDetails(requestDTO.getDetails());
        return payment;
    }

    private Payment createTYPE2Payment_populateSpecificData(CreatePaymentRequestDTO requestDTO) {
        TYPE2Payment payment = new TYPE2Payment();
        payment.setDetails(requestDTO.getDetails());
        return payment;
    }

    private Payment createTYPE3Payment_populateSpecificData(CreatePaymentRequestDTO requestDTO) {
        TYPE3Payment payment = new TYPE3Payment();
        payment.setCreditorBankBIC(requestDTO.getCreditorBankBIC());
        return payment;
    }

    private void populateCommonData(Payment payment, CreatePaymentRequestDTO requestDTO) {
        payment.setType(requestDTO.getType());
        payment.setMoney(new Money(requestDTO.getMoney().getAmount(), requestDTO.getMoney().getCurrency()));
        payment.setDebtor_iban(requestDTO.getDebtor_iban());
        payment.setCreditor_iban(requestDTO.getCreditor_iban());
        payment.setCreatedDate(LocalDate.now());
        payment.setCreatedAt(LocalDateTime.now());
    }
}
