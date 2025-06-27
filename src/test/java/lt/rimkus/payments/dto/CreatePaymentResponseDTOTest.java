package lt.rimkus.payments.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreatePaymentResponseDTOTest {

    @Test
    @DisplayName("Should initialize with empty validationErrors list")
    void shouldInitializeWithEmptyValidationErrors() {
        // Given & When
        CreatePaymentResponseDTO dto = new CreatePaymentResponseDTO();

        // Then
        assertNotNull(dto.getValidationErrors());
        assertTrue(dto.getValidationErrors().isEmpty());
    }

    @Test
    @DisplayName("Should set and get PaymentDTO correctly")
    void shouldSetAndGetPaymentDTO() {
        // Given
        CreatePaymentResponseDTO dto = new CreatePaymentResponseDTO();
        TYPE1PaymentDTO paymentDTO = new TYPE1PaymentDTO();
        paymentDTO.setDetails("Transfer");

        // When
        dto.setPaymentDTO(paymentDTO);

        // Then
        assertEquals(paymentDTO, dto.getPaymentDTO());
        assertEquals("Transfer", ((TYPE1PaymentDTO) dto.getPaymentDTO()).getDetails());
    }

    @Test
    @DisplayName("Should allow adding validation errors to list")
    void shouldAllowAddingValidationErrors() {
        // Given
        CreatePaymentResponseDTO dto = new CreatePaymentResponseDTO();

        // When
        dto.getValidationErrors().add("Missing IBAN");
        dto.getValidationErrors().add("Invalid currency");

        // Then
        List<String> errors = dto.getValidationErrors();
        assertEquals(2, errors.size());
        assertTrue(errors.contains("Missing IBAN"));
        assertTrue(errors.contains("Invalid currency"));
    }
}