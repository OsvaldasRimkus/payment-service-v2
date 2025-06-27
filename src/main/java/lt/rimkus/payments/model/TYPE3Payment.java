package lt.rimkus.payments.model;

import jakarta.persistence.Entity;

@Entity
public class TYPE3Payment extends Payment {
    public String getCreditorBankBIC() {
        return creditorBankBIC;
    }

    public void setCreditorBankBIC(String creditorBankBIC) {
        this.creditorBankBIC = creditorBankBIC;
    }

    private String creditorBankBIC;
}
