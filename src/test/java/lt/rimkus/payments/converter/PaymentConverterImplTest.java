package lt.rimkus.payments.converter;
import lt.rimkus.payments.dto.PaymentDTO;
import lt.rimkus.payments.dto.TYPE1PaymentDTO;
import lt.rimkus.payments.dto.TYPE2PaymentDTO;
import lt.rimkus.payments.dto.TYPE3PaymentDTO;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE2Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentConverterImpl Tests")
class PaymentConverterImplTest {

    @InjectMocks
    private PaymentConverterImpl paymentConverter;

    private Money commonMoney;
    private String commonDebtorIban;
    private String commonCreditorIban;
    private Long commonId;

    @BeforeEach
    void setUp() {
        commonMoney = new Money(new BigDecimal("100.50"), "EUR");
        commonDebtorIban = "DE89370400440532013000";
        commonCreditorIban = "FR1420041010050500013M02606";
        commonId = 1L;
    }

    @Nested
    @DisplayName("TYPE1 Payment Conversion Tests")
    class TYPE1PaymentConversionTests {

        @Test
        @DisplayName("Given valid TYPE1 payment, When converting to DTO, Then should return TYPE1PaymentDTO with all fields mapped")
        void givenValidTYPE1Payment_whenConvertingToDTO_thenShouldReturnTYPE1PaymentDTOWithAllFieldsMapped() {
            // Given
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails("TYPE1 specific details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE1PaymentDTO.class);
            TYPE1PaymentDTO type1Result = (TYPE1PaymentDTO) result;

            // Verify common fields
            assertThat(type1Result.getId()).isEqualTo(commonId);
            assertThat(type1Result.getType()).isEqualTo("TYPE1");
            assertThat(type1Result.getMoney().getAmount()).isEqualTo(commonMoney.getAmount());
            assertThat(type1Result.getMoney().getCurrency()).isEqualTo(commonMoney.getCurrency());
            assertThat(type1Result.getDebtor_iban()).isEqualTo(commonDebtorIban);
            assertThat(type1Result.getCreditor_iban()).isEqualTo(commonCreditorIban);

            // Verify TYPE1 specific fields
            assertThat(type1Result.getDetails()).isEqualTo("TYPE1 specific details");
        }

        @Test
        @DisplayName("Given TYPE1 payment with null details, When converting to DTO, Then should handle null gracefully")
        void givenTYPE1PaymentWithNullDetails_whenConvertingToDTO_thenShouldHandleNullGracefully() {
            // Given
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails(null);

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE1PaymentDTO.class);
            TYPE1PaymentDTO type1Result = (TYPE1PaymentDTO) result;
            assertThat(type1Result.getDetails()).isNull();
        }
    }

    @Nested
    @DisplayName("TYPE2 Payment Conversion Tests")
    class TYPE2PaymentConversionTests {

        @Test
        @DisplayName("Given valid TYPE2 payment, When converting to DTO, Then should return TYPE2PaymentDTO with all fields mapped")
        void givenValidTYPE2Payment_whenConvertingToDTO_thenShouldReturnTYPE2PaymentDTOWithAllFieldsMapped() {
            // Given
            TYPE2Payment payment = new TYPE2Payment();
            payment.setId(commonId);
            payment.setType("TYPE2");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails("TYPE2 specific details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE2PaymentDTO.class);
            TYPE2PaymentDTO type2Result = (TYPE2PaymentDTO) result;

            // Verify common fields
            assertThat(type2Result.getId()).isEqualTo(commonId);
            assertThat(type2Result.getType()).isEqualTo("TYPE2");
            assertThat(type2Result.getMoney().getAmount()).isEqualTo(commonMoney.getAmount());
            assertThat(type2Result.getMoney().getCurrency()).isEqualTo(commonMoney.getCurrency());
            assertThat(type2Result.getDebtor_iban()).isEqualTo(commonDebtorIban);
            assertThat(type2Result.getCreditor_iban()).isEqualTo(commonCreditorIban);

            // Verify TYPE2 specific fields
            assertThat(type2Result.getDetails()).isEqualTo("TYPE2 specific details");
        }

        @Test
        @DisplayName("Given TYPE2 payment with empty details, When converting to DTO, Then should preserve empty string")
        void givenTYPE2PaymentWithEmptyDetails_whenConvertingToDTO_thenShouldPreserveEmptyString() {
            // Given
            TYPE2Payment payment = new TYPE2Payment();
            payment.setId(commonId);
            payment.setType("TYPE2");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails("");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE2PaymentDTO.class);
            TYPE2PaymentDTO type2Result = (TYPE2PaymentDTO) result;
            assertThat(type2Result.getDetails()).isEmpty();
        }
    }

    @Nested
    @DisplayName("TYPE3 Payment Conversion Tests")
    class TYPE3PaymentConversionTests {

        @Test
        @DisplayName("Given valid TYPE3 payment, When converting to DTO, Then should return TYPE3PaymentDTO with all fields mapped")
        void givenValidTYPE3Payment_whenConvertingToDTO_thenShouldReturnTYPE3PaymentDTOWithAllFieldsMapped() {
            // Given
            TYPE3Payment payment = new TYPE3Payment();
            payment.setId(commonId);
            payment.setType("TYPE3");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setCreditorBankBIC("DEUTDEFF");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE3PaymentDTO.class);
            TYPE3PaymentDTO type3Result = (TYPE3PaymentDTO) result;

            // Verify common fields
            assertThat(type3Result.getId()).isEqualTo(commonId);
            assertThat(type3Result.getType()).isEqualTo("TYPE3");
            assertThat(type3Result.getMoney().getAmount()).isEqualTo(commonMoney.getAmount());
            assertThat(type3Result.getMoney().getCurrency()).isEqualTo(commonMoney.getCurrency());
            assertThat(type3Result.getDebtor_iban()).isEqualTo(commonDebtorIban);
            assertThat(type3Result.getCreditor_iban()).isEqualTo(commonCreditorIban);

            // Verify TYPE3 specific fields
            assertThat(type3Result.getCreditorBankBIC()).isEqualTo("DEUTDEFF");
        }

        @Test
        @DisplayName("Given TYPE3 payment with null BIC, When converting to DTO, Then should handle null gracefully")
        void givenTYPE3PaymentWithNullBIC_whenConvertingToDTO_thenShouldHandleNullGracefully() {
            // Given
            TYPE3Payment payment = new TYPE3Payment();
            payment.setId(commonId);
            payment.setType("TYPE3");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setCreditorBankBIC(null);

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result).isInstanceOf(TYPE3PaymentDTO.class);
            TYPE3PaymentDTO type3Result = (TYPE3PaymentDTO) result;
            assertThat(type3Result.getCreditorBankBIC()).isNull();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling Tests")
    class EdgeCasesAndErrorHandlingTests {

        @Test
        @DisplayName("Given payment with invalid type, When converting to DTO, Then should throw IllegalArgumentException")
        void givenPaymentWithInvalidType_whenConvertingToDTO_thenShouldThrowIllegalArgumentException() {
            // Given
            Payment payment = mock(Payment.class);
            when(payment.getType()).thenReturn("INVALID_TYPE");

            // When & Then
            assertThatThrownBy(() -> paymentConverter.convertPaymentToDTO(payment))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("No enum constant")
                    .hasMessageContaining("INVALID_TYPE");
        }

        @Test
        @DisplayName("Given payment with null type, When converting to DTO, Then should throw exception")
        void givenPaymentWithNullType_whenConvertingToDTO_thenShouldThrowException() {
            // Given
            Payment payment = mock(Payment.class);
            when(payment.getType()).thenReturn(null);

            // When & Then
            assertThatThrownBy(() -> paymentConverter.convertPaymentToDTO(payment))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Given null payment, When converting to DTO, Then should throw NullPointerException")
        void givenNullPayment_whenConvertingToDTO_thenShouldThrowNullPointerException() {
            // Given
            Payment payment = null;

            // When & Then
            assertThatThrownBy(() -> paymentConverter.convertPaymentToDTO(payment))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("Money Conversion Tests")
    class MoneyConversionTests {

        @Test
        @DisplayName("Given payment with zero amount, When converting to DTO, Then should preserve zero amount")
        void givenPaymentWithZeroAmount_whenConvertingToDTO_thenShouldPreserveZeroAmount() {
            // Given
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(new Money(BigDecimal.ZERO, "USD"));
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails("Details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result.getMoney().getAmount()).isEqualTo(BigDecimal.ZERO);
            assertThat(result.getMoney().getCurrency()).isEqualTo("USD");
        }

        @Test
        @DisplayName("Given payment with large amount, When converting to DTO, Then should preserve precision")
        void givenPaymentWithLargeAmount_whenConvertingToDTO_thenShouldPreservePrecision() {
            // Given
            BigDecimal largeAmount = new BigDecimal("999999999.99");
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(new Money(largeAmount, "EUR"));
            payment.setDebtor_iban(commonDebtorIban);
            payment.setCreditor_iban(commonCreditorIban);
            payment.setDetails("Details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result.getMoney().getAmount()).isEqualTo(largeAmount);
        }

        @Test
        @DisplayName("Given payment with different currencies, When converting to DTO, Then should preserve currency")
        void givenPaymentWithDifferentCurrencies_whenConvertingToDTO_thenShouldPreserveCurrency() {
            // Given
            String[] currencies = {"USD", "GBP", "JPY", "CHF"};

            for (String currency : currencies) {
                TYPE1Payment payment = new TYPE1Payment();
                payment.setId(commonId);
                payment.setType("TYPE1");
                payment.setMoney(new Money(new BigDecimal("100"), currency));
                payment.setDebtor_iban(commonDebtorIban);
                payment.setCreditor_iban(commonCreditorIban);
                payment.setDetails("Details");

                // When
                PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

                // Then
                assertThat(result.getMoney().getCurrency()).isEqualTo(currency);
            }
        }
    }

    @Nested
    @DisplayName("IBAN Field Tests")
    class IBANFieldTests {

        @Test
        @DisplayName("Given payment with null IBANs, When converting to DTO, Then should handle nulls gracefully")
        void givenPaymentWithNullIBANs_whenConvertingToDTO_thenShouldHandleNullsGracefully() {
            // Given
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban(null);
            payment.setCreditor_iban(null);
            payment.setDetails("Details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result.getDebtor_iban()).isNull();
            assertThat(result.getCreditor_iban()).isNull();
        }

        @Test
        @DisplayName("Given payment with empty IBANs, When converting to DTO, Then should preserve empty strings")
        void givenPaymentWithEmptyIBANs_whenConvertingToDTO_thenShouldPreserveEmptyStrings() {
            // Given
            TYPE1Payment payment = new TYPE1Payment();
            payment.setId(commonId);
            payment.setType("TYPE1");
            payment.setMoney(commonMoney);
            payment.setDebtor_iban("");
            payment.setCreditor_iban("");
            payment.setDetails("Details");

            // When
            PaymentDTO result = paymentConverter.convertPaymentToDTO(payment);

            // Then
            assertThat(result.getDebtor_iban()).isEmpty();
            assertThat(result.getCreditor_iban()).isEmpty();
        }
    }
}