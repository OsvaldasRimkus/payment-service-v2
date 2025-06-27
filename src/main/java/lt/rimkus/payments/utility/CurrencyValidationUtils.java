package lt.rimkus.payments.utility;

import lt.rimkus.payments.enums.Currency;
import lt.rimkus.payments.enums.PaymentType;

import java.util.Map;
import java.util.Set;

public final class CurrencyValidationUtils {

    private CurrencyValidationUtils() {
        // Utility class cannot be instantiated
    }

    private static final Map<PaymentType, Set<Currency>> PAYMENT_TYPE_AND_CURRENCY_MAP =
            Map.of(
                    PaymentType.TYPE1, Set.of(Currency.EUR),
                    PaymentType.TYPE2, Set.of(Currency.USD),
                    PaymentType.TYPE3, Set.of(Currency.EUR, Currency.USD)
            );

    public static boolean isCurrencyNotValid(String currencyCode) {
        return !Currency.isValidCurrency(currencyCode);
    }

    public static boolean isCurrencyNotValidForPaymentType(String currencyCode, String paymentTypeCode) {
        if (!Currency.isValidCurrency(currencyCode) || !PaymentType.isValidType(paymentTypeCode)) {
            return true;
        }
        Currency currency = Currency.resolveCurrencyFromCode(currencyCode);
        PaymentType paymentType = PaymentType.resolvePaymentTypeFromCode(paymentTypeCode);
        Set<Currency> allowedCurrencies = PAYMENT_TYPE_AND_CURRENCY_MAP.get(paymentType);
        return allowedCurrencies == null || !allowedCurrencies.contains(currency);
    }
}

