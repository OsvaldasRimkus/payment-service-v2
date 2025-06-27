package lt.rimkus.payments.dto;

import lt.rimkus.payments.model.Money;

public class PaymentCancellationInfoDTO {
    private Long id;
    private Money cancellationFee;

    public PaymentCancellationInfoDTO(Long id, Money cancellationFee) {
        this.id = id;
        this.cancellationFee = cancellationFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Money cancellationFee) {
        this.cancellationFee = cancellationFee;
    }
}
