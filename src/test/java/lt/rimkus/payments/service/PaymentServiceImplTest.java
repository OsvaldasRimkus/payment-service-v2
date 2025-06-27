package lt.rimkus.payments.service;

import lt.rimkus.payments.converter.PaymentConverter;
import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.dto.GetNotCancelledPaymentsDTO;
import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.dto.PaymentDTO;
import lt.rimkus.payments.dto.TYPE1PaymentDTO;
import lt.rimkus.payments.factory.PaymentCreationFactory;
import lt.rimkus.payments.model.Money;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.model.TYPE1Payment;
import lt.rimkus.payments.model.TYPE3Payment;
import lt.rimkus.payments.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentServiceImplTest {

    private PaymentRepository paymentRepository;
    private PaymentCreationFactory paymentCreationFactory;
    private PaymentConverter paymentConverter;
    private PaymentCancellationService paymentCancellationService;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentCreationFactory = mock(PaymentCreationFactory.class);
        paymentConverter = mock(PaymentConverter.class);
        paymentCancellationService = mock(PaymentCancellationService.class);

        paymentService = new PaymentServiceImpl(
                paymentRepository, paymentCreationFactory, paymentConverter, paymentCancellationService
        );
    }

    @Test
    @DisplayName("Should return all payments")
    void shouldReturnAllPayments() {
        // Given
        List<Payment> mockPayments = List.of(new TYPE1Payment(), new TYPE3Payment());
        when(paymentRepository.findAll()).thenReturn(mockPayments);

        // When
        List<Payment> result = paymentService.getAllPayments();

        // Then
        assertThat(result).isEqualTo(mockPayments);
        verify(paymentRepository).findAll();
    }

    @Test
    @DisplayName("Should save payment and return response DTO")
    void shouldSavePaymentAndReturnDTO() {
        // Given
        CreatePaymentRequestDTO requestDTO = new CreatePaymentRequestDTO();
        CreatePaymentResponseDTO responseDTO = new CreatePaymentResponseDTO();
        Payment payment = new TYPE1Payment();
        PaymentDTO paymentDTO = new TYPE1PaymentDTO();

        when(paymentCreationFactory.createNewPayment(requestDTO)).thenReturn(payment);
        when(paymentConverter.convertPaymentToDTO(payment)).thenReturn(paymentDTO);

        // When
        CreatePaymentResponseDTO result = paymentService.savePayment(requestDTO, responseDTO);

        // Then
        assertThat(result.getPaymentDTO()).isEqualTo(paymentDTO);
        verify(paymentRepository).save(payment);
        verify(paymentConverter).convertPaymentToDTO(payment);
    }

    @Test
    @DisplayName("Should cancel payment if exists and valid")
    void shouldCancelPaymentSuccessfully() {
        // Given
        Long paymentId = 1L;
        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();
        Payment payment = new TYPE1Payment();
        payment.setId(paymentId);
        payment.setCreatedDate(LocalDate.now());
        payment.setCreatedAt(LocalDateTime.now().minusHours(2));
        payment.setMoney(new Money(BigDecimal.valueOf(100), "EUR"));
        payment.setCancellationFee(new Money());

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentCancellationService.isPaymentPreparedForCancellation(eq(payment), any(), any(), eq(responseDTO)))
                .thenReturn(true);

        PaymentDTO dto = new TYPE1PaymentDTO();
        when(paymentConverter.convertPaymentToDTO(payment)).thenReturn(dto);

        // When
        CancelPaymentResponseDTO result = paymentService.cancelPayment(paymentId, responseDTO);

        // Then
        assertThat(result.getPaymentDTO()).isEqualTo(dto);
        assertThat(result.getCancellationFee()).isNotNull();
        verify(paymentRepository).save(payment);
    }

    @Test
    @DisplayName("Should return error if payment not found")
    void shouldReturnErrorIfPaymentNotFound() {
        // Given
        Long paymentId = 1L;
        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // When
        CancelPaymentResponseDTO result = paymentService.cancelPayment(paymentId, responseDTO);

        // Then
        assertThat(result.getMessage()).isEqualTo("Provided payment id does not exist");
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return not-cancelled payment IDs when filter is true")
    void shouldReturnNotCancelledPaymentIdsWhenFilterIsOn() {
        // Given
        GetNotCancelledPaymentsDTO requestDTO = new GetNotCancelledPaymentsDTO();
        requestDTO.setFilter(true);
        requestDTO.setMinAmount(BigDecimal.valueOf(10));
        requestDTO.setMaxAmount(BigDecimal.valueOf(100));

        List<Long> ids = List.of(1L, 2L);
        when(paymentRepository.getNotCancelledPaymentsWithinRange(
                requestDTO.getMinAmount(), requestDTO.getMaxAmount()
        )).thenReturn(ids);

        // When
        List<Long> result = paymentService.getNotCanceledPaymentIds(requestDTO);

        // Then
        assertThat(result).isEqualTo(ids);
    }

    @Test
    @DisplayName("Should return all not-cancelled payment IDs when filter is off")
    void shouldReturnAllNotCancelledPaymentIdsWhenFilterIsOff() {
        // Given
        GetNotCancelledPaymentsDTO requestDTO = new GetNotCancelledPaymentsDTO();
        requestDTO.setFilter(false);
        List<Long> ids = List.of(5L);
        when(paymentRepository.getNotCancelledPaymentsWithinRange(null, null)).thenReturn(ids);

        // When
        List<Long> result = paymentService.getNotCanceledPaymentIds(requestDTO);

        // Then
        assertThat(result).isEqualTo(ids);
    }

    @Test
    @DisplayName("Should return payment cancellation details")
    void shouldReturnCancellationDetails() {
        // Given
        Long id = 99L;
        PaymentCancellationInfoDTO dto = new PaymentCancellationInfoDTO(id, new Money(BigDecimal.TEN, "EUR"));
        when(paymentRepository.getPaymentCancellationDetails(id)).thenReturn(dto);

        // When
        PaymentCancellationInfoDTO result = paymentService.getPaymentCancellationDetails(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }
}