package lt.rimkus.payments.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PaymentType {
    TYPE1("TYPE1"),
    TYPE2("TYPE2"),
    TYPE3("TYPE3");

    private final String code;

    PaymentType(String code) {
        this.code = code;
    }

    private static final Map<String, PaymentType> VALID_TYPES =
            Arrays.stream(values()).collect(Collectors.toMap(Enum::name, e -> e));

    public static boolean isValidType(String type) {
        return VALID_TYPES.containsKey(type);
    }

    public static PaymentType resolvePaymentTypeFromCode(String code) {
        return VALID_TYPES.get(code);
    }

    public String getCode() {
        return code;
    }
}
