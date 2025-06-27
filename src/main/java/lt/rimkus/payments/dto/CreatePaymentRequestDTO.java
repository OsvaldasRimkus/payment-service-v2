package lt.rimkus.payments.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lt.rimkus.payments.validation.CreatePaymentRequestValidation;

import java.math.BigDecimal;

@CreatePaymentRequestValidation
public class CreatePaymentRequestDTO {
    @NotBlank(message = "Payment type missing")
    private String type;
    @NotNull(message = "Money must not be null")
    @Valid
    private MoneyDTO money;
    @NotBlank(message = "Debtor IBAN missing")
    private String debtor_iban;
    @NotBlank(message = "Creditor IBAN missing")
    private String creditor_iban;
    // mandatory only for TYPE3
    private String creditorBankBIC;
    // mandatory for TYPE1, optional for TYPE3.
    private String details;

    public CreatePaymentRequestDTO() {
    }

    public CreatePaymentRequestDTO(String type, BigDecimal amount, String currency, String debtorIban, String creditorIban) {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MoneyDTO getMoney() {
        return money;
    }

    public void setMoney(MoneyDTO money) {
        this.money = money;
    }

    public String getDebtor_iban() {
        return debtor_iban;
    }

    public void setDebtor_iban(String debtor_iban) {
        this.debtor_iban = debtor_iban;
    }

    public String getCreditor_iban() {
        return creditor_iban;
    }

    public void setCreditor_iban(String creditor_iban) {
        this.creditor_iban = creditor_iban;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreditorBankBIC() {
        return creditorBankBIC;
    }

    public void setCreditorBankBIC(String creditorBankBIC) {
        this.creditorBankBIC = creditorBankBIC;
    }
}
