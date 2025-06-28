package lt.rimkus.payments.message;

public class ResponseMessages {
    public static final String PAYMENT_WITH_ID = "Payment with id ";
    public static final String IS_ALREADY_CANCELED = " is already canceled";
    public static final String SAME_DAY_CANCELLATION_ONLY = "Payment can be cancelled only on the day of its creation";
    public static final String WAS_CANCELLED_WITH_FEE = " was successfully cancelled. Cancellation fee is: ";
    public static final String PAYMENT_DOES_NOT_EXIST = "Provided payment id does not exist";
    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";
}
