package lt.rimkus.payments.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lt.rimkus.payments.dto.CreatePaymentRequestDTO;
import lt.rimkus.payments.dto.CreatePaymentResponseDTO;
import lt.rimkus.payments.model.Payment;
import lt.rimkus.payments.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        if (!responseDTO.getValidationErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }

}
