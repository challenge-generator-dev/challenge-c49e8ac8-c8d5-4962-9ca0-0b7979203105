package com.pragma.paymentservice.application;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.model.PaymentResponse.PaymentStatus;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentPersistencePort persistencePort;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(persistencePort);
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando no existe clave de idempotencia")
    void shouldProcessPaymentSuccessfullyWhenIdempotencyKeyDoesNotExist() {
        PaymentRequest request = createValidRequest();
        PaymentResponse expectedResponse = PaymentResponse.success(
            "OP-001", "Pago procesado correctamente", "12345"
        );

        when(persistencePort.findByIdempotencyKey(anyString())).thenReturn(Optional.empty());
        when(persistencePort.save(any(PaymentResponse.class), anyString(), anyString()))
            .thenReturn(expectedResponse);

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        verify(persistencePort).findByIdempotencyKey(anyString());
        verify(persistencePort).save(any(PaymentResponse.class), anyString(), anyString());
    }

    @Test
    @DisplayName("Debe devolver pago existente cuando clave de idempotencia ya existe")
    void shouldReturnExistingPaymentWhenIdempotencyKeyExists() {
        PaymentRequest request = createValidRequest();
        PaymentResponse existingResponse = PaymentResponse.success(
            "OP-001", "Pago existente", "12345"
        );

        when(persistencePort.findByIdempotencyKey(anyString()))
            .thenReturn(Optional.of(existingResponse));

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        assertEquals("Pago existente", result.join().message());
        verify(persistencePort, never()).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Debe fallar cuando la solicitud es inválida")
    void shouldFailWhenRequestIsInvalid() {
        PaymentRequest invalidRequest = new PaymentRequest(
            null, null, BigDecimal.ZERO, null, null
        );

        CompletableFuture<PaymentResponse> result = paymentService.processPayment(invalidRequest);

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILURE, result.join().status());
    }

    @Test
    @DisplayName("Debe verificar estado de pago correctamente")
    void shouldCheckPaymentStatusCorrectly() {
        String operationNumber = "OP-001";
        String channel = "WEB";
        PaymentResponse existingResponse = PaymentResponse.success(
            operationNumber, "Pago encontrado", "12345"
        );

        when(persistencePort.findByOperationNumberAndChannel(operationNumber, channel))
            .thenReturn(Optional.of(existingResponse));

        CompletableFuture<PaymentResponse> result = 
            paymentService.checkPaymentStatus(operationNumber, channel);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS, result.join().status());
        verify(persistencePort).findByOperationNumberAndChannel(operationNumber, channel);
    }

    @Test
    @DisplayName("Debe devolver failure cuando pago no existe al consultar estado")
    void shouldReturnFailureWhenPaymentNotFoundOnStatusCheck() {
        String operationNumber = "OP-999";
        String channel = "MOBILE";

        when(persistencePort.findByOperationNumberAndChannel(operationNumber, channel))
            .thenReturn(Optional.empty());

        CompletableFuture<PaymentResponse> result = 
            paymentService.checkPaymentStatus(operationNumber, channel);

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILURE, result.join().status());
    }

    private PaymentRequest createValidRequest() {
        return new PaymentRequest(
            "OP-001",
            "WEB",
            new BigDecimal("1500.00"),
            "1234567890",
            "Pago de servicio"
        );
    }
}