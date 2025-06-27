package lt.rimkus.payments.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Currency {
    EUR("EUR"),
    USD("USD");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    private static final Map<String, Currency> VALID_CURRENCIES =
            Arrays.stream(values()).collect(Collectors.toMap(Enum::name, e -> e));

    public static boolean isValidCurrency(String code) {
        return VALID_CURRENCIES.containsKey(code);
    }

    public static Currency resolveCurrencyFromCode(String code) {
        return VALID_CURRENCIES.get(code);
    }

    public String getCode() {
        return code;
    }
}
