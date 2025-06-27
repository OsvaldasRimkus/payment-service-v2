package lt.rimkus.payments.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.rimkus.payments.model.Money;

public class CancelPaymentResponseDTO {
    private String message;
    @JsonProperty("payment")
    @JsonIgnore
    private PaymentDTO paymentDTO;
    private Money cancellationFee;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    public Money getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Money cancellationFee) {
        this.cancellationFee = cancellationFee;
    }
}
