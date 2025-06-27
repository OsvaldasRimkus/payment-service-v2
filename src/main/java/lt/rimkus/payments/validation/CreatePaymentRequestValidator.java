package lt.rimkus.payments.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.enums.PaymentType;
import lt.rimkus.payments.utility.CurrencyValidationUtils;
import lt.rimkus.payments.utility.PaymentTypeValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class CreatePaymentRequestValidator implements ConstraintValidator<CreatePaymentRequestValidation, CreatePaymentRequestDTO> {
    @Override
    public boolean isValid(CreatePaymentRequestDTO requestDTO, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (requestDTO == null || StringUtils.isBlank(requestDTO.getType()) || requestDTO.getMoney() == null) {
            return isValid; // @NotNull should handle null validations
        }
        context.disableDefaultConstraintViolation();

        if (PaymentType.TYPE1.getCode().equals(requestDTO.getType()) && StringUtils.isBlank(requestDTO.getDetails())) {
            isValid &= addViolation(context, "Details are mandatory when payment type is " + PaymentType.TYPE1.getCode(), "details");
        }
        if (PaymentType.TYPE3.getCode().equals(requestDTO.getType()) && StringUtils.isBlank(requestDTO.getCreditorBankBIC())) {
            isValid &= addViolation(context, "Creditor Bank BIC is mandatory when payment type is " + PaymentType.TYPE3.getCode(), "creditorBankBIC");
        }
        if (PaymentTypeValidationUtils.isPaymentTypeNotValid(requestDTO.getType())) {
            isValid &= addViolation(context, "Payment type is not valid", "type_valid");
        }
        if (CurrencyValidationUtils.isCurrencyNotValid(requestDTO.getMoney().getCurrency())) {
            isValid &= addViolation(context, "Currency type is not valid", "money.currency_valid");
        }
        if (CurrencyValidationUtils.isCurrencyNotValidForPaymentType(requestDTO.getMoney().getCurrency(), requestDTO.getType())) {
            isValid &= addViolation(context, "Currency type is not valid for payment type", "money.currency_compatible");
        }
        return isValid;
    }

    private boolean addViolation(ConstraintValidatorContext context, String message, String propertyNode) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyNode)
                .addConstraintViolation();
        return false;
    }
}
