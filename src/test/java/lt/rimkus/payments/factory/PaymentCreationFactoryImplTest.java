package lt.rimkus.payments.factory;

import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.MoneyDTO;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentCreationFactoryImplTest {

    private final PaymentCreationFactoryImpl factory = new PaymentCreationFactoryImpl();

    private CreatePaymentRequestDTO buildBaseDTO(String type) {
        CreatePaymentRequestDTO dto = new CreatePaymentRequestDTO();
        dto.setType(type);
        dto.setMoney(new MoneyDTO(BigDecimal.valueOf(100.50), "EUR"));
        dto.setDebtor_iban("DE12345678901234567890");
        dto.setCreditor_iban("FR98765432109876543210");
        dto.setDetails("Details info");
        dto.setCreditorBankBIC("BANKBICXXX");
        return dto;
    }

    @Test
    @DisplayName("Should create TYPE1Payment with correct specific and common fields")
    void shouldCreateType1Payment() {
        // Given
        CreatePaymentRequestDTO dto = buildBaseDTO("TYPE1");

        // When
        Payment payment = factory.createNewPayment(dto);

        // Then
        assertInstanceOf(TYPE1Payment.class, payment);
        assertEquals("Details info", ((TYPE1Payment) payment).getDetails());
        assertCommonFields(payment, dto);
    }

    @Test
    @DisplayName("Should create TYPE2Payment with correct specific and common fields")
    void shouldCreateType2Payment() {
        // Given
        CreatePaymentRequestDTO dto = buildBaseDTO("TYPE2");

        // When
        Payment payment = factory.createNewPayment(dto);

        // Then
        assertInstanceOf(TYPE2Payment.class, payment);
        assertEquals("Details info", ((TYPE2Payment) payment).getDetails());
        assertCommonFields(payment, dto);
    }

    @Test
    @DisplayName("Should create TYPE3Payment with correct specific and common fields")
    void shouldCreateType3Payment() {
        // Given
        CreatePaymentRequestDTO dto = buildBaseDTO("TYPE3");

        // When
        Payment payment = factory.createNewPayment(dto);

        // Then
        assertInstanceOf(TYPE3Payment.class, payment);
        assertEquals("BANKBICXXX", ((TYPE3Payment) payment).getCreditorBankBIC());
        assertCommonFields(payment, dto);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unknown type")
    void shouldThrowExceptionForUnknownType() {
        // Given
        CreatePaymentRequestDTO dto = buildBaseDTO("UNKNOWN");

        // Then
        assertThrows(IllegalArgumentException.class, () -> factory.createNewPayment(dto));
    }

    private void assertCommonFields(Payment payment, CreatePaymentRequestDTO dto) {
        assertEquals(dto.getType(), payment.getType());
        assertNotNull(payment.getMoney());
        assertEquals(dto.getMoney().getAmount(), payment.getMoney().getAmount());
        assertEquals(dto.getMoney().getCurrency(), payment.getMoney().getCurrency());
        assertEquals(dto.getDebtor_iban(), payment.getDebtor_iban());
        assertEquals(dto.getCreditor_iban(), payment.getCreditor_iban());
        assertEquals(LocalDate.now(), payment.getCreatedDate());
        assertNotNull(payment.getCreatedAt());
    }
}