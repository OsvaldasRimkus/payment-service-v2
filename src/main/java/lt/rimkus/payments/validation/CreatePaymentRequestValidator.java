package lt.rimkus.payments.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.utility.CurrencyValidationUtils;
import lt.rimkus.payments.utility.PaymentTypeValidationUtils;
import org.apache.commons.lang3.StringUtils;

public class CreatePaymentRequestValidator implements ConstraintValidator<CreatePaymentRequestValidation, CreatePaymentRequestDTO> {
    @Override
    public boolean isValid(CreatePaymentRequestDTO requestDTO, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (requestDTO == null || requestDTO.getMoney() == null) {
            return isValid; // @NotNull should handle null validations
        }
        context.disableDefaultConstraintViolation();

        if ("TYPE1".equals(requestDTO.getType()) && StringUtils.isEmpty(requestDTO.getDetails())) {
            isValid &= addViolation(context, isValid, "Details are mandatory when payment type is TYPE1", "details");
        }
        if ("TYPE3".equals(requestDTO.getType()) && StringUtils.isEmpty(requestDTO.getCreditorBankBIC())) {
            isValid &= addViolation(context, isValid, "Creditor Bank BIC is mandatory when payment type is TYPE3", "creditorBankBIC");
        }
        if (PaymentTypeValidationUtils.isPaymentTypeNotValid(requestDTO.getType())) {
            isValid &= addViolation(context, isValid, "Payment type is not valid", "type_valid");
        }
        if (CurrencyValidationUtils.isCurrencyNotValid(requestDTO.getMoney().getCurrency())) {
            isValid &= addViolation(context, isValid, "Currency type is not valid", "money.currency_valid");
        }
        if (CurrencyValidationUtils.isCurrencyNotValidForPaymentType(requestDTO.getMoney().getCurrency(), requestDTO.getType())) {
            isValid &= addViolation(context, isValid, "Currency type is not valid for payment type", "money.currency_compatible");
        }
        return isValid;
    }

    private boolean addViolation(ConstraintValidatorContext context, boolean isValid, String message, String propertyNode) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyNode)
                .addConstraintViolation();
        return false;
    }
}
