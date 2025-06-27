package lt.rimkus.payments.dto;

public class TYPE3PaymentDTO extends PaymentDTO {
    private String creditorBankBIC;

    public String getCreditorBankBIC() {
        return creditorBankBIC;
    }

    public void setCreditorBankBIC(String creditorBankBIC) {
        this.creditorBankBIC = creditorBankBIC;
    }
}
