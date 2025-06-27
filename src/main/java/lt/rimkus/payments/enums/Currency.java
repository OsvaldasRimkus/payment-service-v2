package lt.rimkus.payments.enums;

import java.util.Arrays;

public enum Currency {
    EUR("EUR"),
    USD("USD");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public static boolean isCurrencyValid(String code) {
        for (Currency currencyType : values()) {
            if (currencyType.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static Currency fromCode(String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(values())
                .filter(c -> c.code.equals(code.trim()))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
