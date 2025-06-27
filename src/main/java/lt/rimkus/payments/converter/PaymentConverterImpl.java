package lt.rimkus.payments.converter;

import lt.rimkus.payments.dto.MoneyDTO;
import lt.rimkus.payments.dto.PaymentDTO;
import lt.rimkus.payments.dto.TYPE1PaymentDTO;
import lt.rimkus.payments.dto.TYPE2PaymentDTO;
import lt.rimkus.payments.dto.TYPE3PaymentDTO;
import lt.rimkus.payments.enums.PaymentType;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverterImpl implements PaymentConverter {

    @Override
    public PaymentDTO convertPaymentToDTO(Payment payment) {
        PaymentType paymentType = PaymentType.valueOf(payment.getType());
        PaymentDTO paymentDTO = switch (paymentType) {
            case TYPE1 -> convertToTYPE1PaymentDTO((TYPE1Payment) payment);
            case TYPE2 -> convertToTYPE2PaymentDTO((TYPE2Payment) payment);
            case TYPE3 -> convertToTYPE3PaymentDTO((TYPE3Payment) payment);
        };
        mapCommonData(paymentDTO, payment);
        return paymentDTO;
    }

    private PaymentDTO convertToTYPE1PaymentDTO(TYPE1Payment payment) {
        TYPE1PaymentDTO paymentDTO = new TYPE1PaymentDTO();
        paymentDTO.setDetails(payment.getDetails());
        return paymentDTO;
    }

    private PaymentDTO convertToTYPE2PaymentDTO(TYPE2Payment payment) {
        TYPE2PaymentDTO paymentDTO = new TYPE2PaymentDTO();
        paymentDTO.setDetails(payment.getDetails());
        return paymentDTO;
    }

    private PaymentDTO convertToTYPE3PaymentDTO(TYPE3Payment payment) {
        TYPE3PaymentDTO paymentDTO = new TYPE3PaymentDTO();
        paymentDTO.setCreditorBankBIC(payment.getCreditorBankBIC());
        return paymentDTO;
    }

    private void mapCommonData(PaymentDTO paymentDTO, Payment payment) {
        paymentDTO.setId(payment.getId());
        paymentDTO.setType(payment.getType());
        paymentDTO.setMoney(new MoneyDTO(payment.getMoney().getAmount(), payment.getMoney().getCurrency()));
        paymentDTO.setDebtor_iban(payment.getDebtor_iban());
        paymentDTO.setCreditor_iban(payment.getCreditor_iban());
    }
}
