package lt.rimkus.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.rimkus.payments.dto.*;
import lt.rimkus.payments.model.*;
import lt.rimkus.payments.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return all payments")
    void shouldReturnAllPayments() throws Exception {
        // Given
        Payment payment1 = new TYPE1Payment();
        Payment payment2 = new TYPE3Payment();
        Mockito.when(paymentService.getAllPayments()).thenReturn(List.of(payment1, payment2));

        // When & Then
        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    @DisplayName("Should create a new payment and return response with 201")
    void shouldCreateNewPayment() throws Exception {
        // Given
        CreatePaymentRequestDTO request = new CreatePaymentRequestDTO();
        request.setType("TYPE1");
        request.setMoney(new MoneyDTO(BigDecimal.TEN, "EUR"));
        request.setCreditor_iban("Cred IBAN");
        request.setDebtor_iban("Deb IBAN");
        request.setDetails("details");
        CreatePaymentResponseDTO response = new CreatePaymentResponseDTO();
        PaymentDTO paymentDTO = new TYPE1PaymentDTO();
        response.setPaymentDTO(paymentDTO);

        Mockito.when(paymentService.savePayment(any(), any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.payment").exists());
    }

    @Test
    @DisplayName("Should cancel payment and return success response")
    void shouldCancelPaymentSuccessfully() throws Exception {
        // Given
        Long paymentId = 1L;
        CancelPaymentResponseDTO response = new CancelPaymentResponseDTO();
        response.setPaymentDTO(new TYPE1PaymentDTO());
        response.setCancellationFee(new Money(BigDecimal.TEN, "EUR"));

        Mockito.when(paymentService.cancelPayment(eq(paymentId), any())).thenReturn(response);

        // When & Then
        mockMvc.perform(delete("/api/payments/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message", is("Payment with id 1 was successfully cancelled. Cancellation fee is: 10 EUR")))
                .andExpect(jsonPath("$.cancellationFee.amount", is(10)));
    }

    @Test
    @DisplayName("Should return 404 when payment to cancel is not found")
    void shouldReturnNotFoundWhenCancellingMissingPayment() throws Exception {
        // Given
        Long paymentId = 99L;
        CancelPaymentResponseDTO response = new CancelPaymentResponseDTO();
        response.setMessage("Provided payment id does not exist");

        Mockito.when(paymentService.cancelPayment(eq(paymentId), any())).thenReturn(response);

        // When & Then
        mockMvc.perform(delete("/api/payments/{paymentId}", paymentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Provided payment id does not exist")))
                .andExpect(jsonPath("$.cancellationFee.amount").doesNotExist());
    }

    @Test
    @DisplayName("Should return not-cancelled payment IDs")
    void shouldReturnNotCancelledPaymentIds() throws Exception {
        // Given
        GetNotCancelledPaymentsDTO request = new GetNotCancelledPaymentsDTO();
        List<Long> ids = List.of(1L, 2L);

        Mockito.when(paymentService.getNotCanceledPaymentIds(any())).thenReturn(ids);

        // When & Then
        mockMvc.perform(post("/api/payments/querying/notCancelled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(1)))
                .andExpect(jsonPath("$[1]", is(2)));
    }

    @Test
    @DisplayName("Should return payment cancellation details")
    void shouldReturnCancellationDetails() throws Exception {
        // Given
        Long paymentId = 42L;
        PaymentCancellationInfoDTO response = new PaymentCancellationInfoDTO(paymentId, new Money(BigDecimal.ONE, "USD"));
        Mockito.when(paymentService.getPaymentCancellationDetails(paymentId)).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/payments/querying/cancellationDetails/{paymentId}", paymentId)
                        .param("paymentId", String.valueOf(paymentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(42)))
                .andExpect(jsonPath("$.cancellationFee.amount", is(1)));
    }
}