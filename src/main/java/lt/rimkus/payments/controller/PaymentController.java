package lt.rimkus.payments.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lt.rimkus.payments.dto.CancelPaymentResponseDTO;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.dto.GetNotCancelledPaymentsDTO;
import lt.rimkus.payments.dto.PaymentCancellationInfoDTO;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lt.rimkus.payments.message.ResponseMessages.PAYMENT_WITH_ID;
import static lt.rimkus.payments.message.ResponseMessages.WAS_CANCELLED_WITH_FEE;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all payments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping
    @Operation(summary = "Create a new payment")
    public ResponseEntity<CreatePaymentResponseDTO> createPayment(@RequestBody @Valid CreatePaymentRequestDTO newPayment) {
        CreatePaymentResponseDTO responseDTO = new CreatePaymentResponseDTO();
        responseDTO = paymentService.savePayment(newPayment, responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{paymentId}")
    @Operation(summary = "Cancel an existing payment")
    public ResponseEntity<CancelPaymentResponseDTO> cancelPayment(@PathVariable Long paymentId) {
        CancelPaymentResponseDTO responseDTO = new CancelPaymentResponseDTO();
        responseDTO = paymentService.cancelPayment(paymentId, responseDTO);
        if (responseDTO.getPaymentDTO() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        } else {
            responseDTO.setMessage(PAYMENT_WITH_ID + paymentId + WAS_CANCELLED_WITH_FEE + responseDTO.getCancellationFee().getAmount() + " " + responseDTO.getCancellationFee().getCurrency());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @Operation(summary = "Get all payments that are not canceled")
    @RequestMapping(value = "querying/notCancelled", method = RequestMethod.POST)
    public ResponseEntity<List<Long>> getNotCanceledPaymentIds(@RequestBody @Valid GetNotCancelledPaymentsDTO requestDTO) {
        List<Long> responseDTO = paymentService.getNotCanceledPaymentIds(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Operation(summary = "Get payment cancellation details")
    @RequestMapping(value = "querying/cancellationDetails/{paymentId}", method = RequestMethod.POST)
    public ResponseEntity<PaymentCancellationInfoDTO> getPaymentCancellationDetails(@PathVariable Long paymentId) {
        PaymentCancellationInfoDTO responseDTO = paymentService.getPaymentCancellationDetails(paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
