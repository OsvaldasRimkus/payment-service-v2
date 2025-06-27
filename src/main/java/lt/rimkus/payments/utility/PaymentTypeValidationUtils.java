package lt.rimkus.payments.utility;

import lt.rimkus.payments.enums.PaymentType;

public final class PaymentTypeValidationUtils {

    private PaymentTypeValidationUtils() {
        // Utility class cannot be instantiated
    }

    public static boolean isPaymentTypeNotValid(String paymentTypeCode) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.getCode().equals(paymentTypeCode)) {
                return false;
            }
        }
        return true;
    }
}
