package lt.rimkus.payments.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CreatePaymentRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePaymentRequestValidation {
    String message() default "Field is mandatory.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
