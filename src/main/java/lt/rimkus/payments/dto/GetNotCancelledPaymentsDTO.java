package lt.rimkus.payments.dto;

import java.math.BigDecimal;

public class GetNotCancelledPaymentsDTO {
    boolean filter;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
}
