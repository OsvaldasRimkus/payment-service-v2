package lt.rimkus.payments.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Embeddable
public class Money {
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "currency", nullable = false)
    private String currency;

    public Money() {
    }

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull BigDecimal amount) {
        assert amount != null;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NotNull String currency) {
        assert currency != null;
        this.currency = currency.trim();
    }
}
