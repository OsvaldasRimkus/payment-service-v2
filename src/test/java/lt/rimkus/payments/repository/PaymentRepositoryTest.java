package lt.rimkus.payments.repository;

import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("Should return not-cancelled payments within amount range")
    void shouldReturnNotCancelledPaymentsWithinAmountRange() {
        // Given
        Payment payment1 = createPayment(new BigDecimal("50.00"), false); // matches
        Payment payment2 = createPayment(new BigDecimal("100.00"), false); // matches
        Payment payment3 = createPayment(new BigDecimal("200.00"), true); // cancelled

        paymentRepository.saveAll(List.of(payment1, payment2, payment3));

        // When
        List<Long> result = paymentRepository.getNotCancelledPaymentsWithinRange(
                new BigDecimal("30.00"), new BigDecimal("150.00"));

        // Then
        assertThat(result).containsExactlyInAnyOrder(payment1.getId(), payment2.getId());
    }

    @Test
    @DisplayName("Should return cancellation info for a given payment ID")
    void shouldReturnCancellationInfo() {
        // Given
        Payment payment = createPayment(new BigDecimal("150.00"), true);
        Money cancellationFee = new Money(new BigDecimal("15.00"), "EUR");
        payment.setCancellationFee(cancellationFee);
        payment = paymentRepository.save(payment);

        // When
        PaymentCancellationInfoDTO dto = paymentRepository.getPaymentCancellationDetails(payment.getId());

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(payment.getId());
        assertThat(dto.getCancellationFee().getCurrency()).isEqualTo(cancellationFee.getCurrency());
        assertThat(dto.getCancellationFee().getAmount()).isEqualTo(cancellationFee.getAmount());
    }

    private Payment createPayment(BigDecimal amount, boolean cancelled) {
        Payment payment = new TYPE1Payment();
        payment.setType("TYPE1");
        payment.setDebtor_iban("DE123");
        payment.setCreditor_iban("FR456");
        payment.setCreatedDate(LocalDate.now());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCancelled(cancelled);
        payment.setMoney(new Money(amount, "EUR"));
        return payment;
    }
}