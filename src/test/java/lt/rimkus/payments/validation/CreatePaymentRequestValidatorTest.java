package lt.rimkus.payments.validation;

import jakarta.validation.ConstraintValidatorContext;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.MoneyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("CreatePaymentRequestValidator Validation Tests")
public class CreatePaymentRequestValidatorTest {

    private CreatePaymentRequestValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CreatePaymentRequestValidator();
        context = mock(ConstraintValidatorContext.class);

        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class));
    }

    @Test
    @DisplayName("Should return true when DTO is null")
    void shouldReturnTrueWhenDTOIsNull() {
        // Given
        CreatePaymentRequestDTO dto = null;

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return true when type is blank and rely on other validations")
    void shouldReturnTrueWhenTypeIsBlank() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType(" ");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(100.0), "EUR"));

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should add violation when type is invalid")
    void shouldAddViolationForInvalidPaymentType() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType("INVALID_TYPE");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(10.0), "EUR"));

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should add violation when currency is invalid")
    void shouldAddViolationForInvalidCurrency() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType("TYPE1");
        dto.setDetails("Some details");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(10.0), "XYZ")); // Invalid currency

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should add violation when TYPE1 and details are blank")
    void shouldAddViolationForMissingDetailsInTYPE1() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType("TYPE1");
        dto.setDetails(" ");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(10.0), "EUR"));

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should add violation when TYPE3 and creditorBankBIC is blank")
    void shouldAddViolationForMissingCreditorBICInTYPE3() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType("TYPE3");
        dto.setCreditorBankBIC(" ");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(10.0), "EUR"));

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should return true for valid TYPE1 payment")
    void shouldReturnTrueForValidTYPE1() {
        // Given
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType("TYPE1");
        dto.setDetails("valid");
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(10.0), "EUR"));

        // When
        boolean isValid = validator.isValid(dto, context);

        // Then
        assertTrue(isValid);
    }
}