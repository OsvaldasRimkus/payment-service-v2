package lt.rimkus.payments.repository;

import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT id FROM Payment p " +
            "WHERE p.cancelled = false " +
            "AND (:minAmount IS NULL OR p.money.amount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR p.money.amount <= :maxAmount)")
    List<Long> getNotCancelledPaymentsWithinRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);

    @Query("SELECT new lt.rimkus.payments.dto.PaymentCancellationInfoDTO(p.id, p.cancellationFee) FROM Payment p WHERE p.id = :id")
    PaymentCancellationInfoDTO getPaymentCancellationDetails(@Param("id") Long id);
}
