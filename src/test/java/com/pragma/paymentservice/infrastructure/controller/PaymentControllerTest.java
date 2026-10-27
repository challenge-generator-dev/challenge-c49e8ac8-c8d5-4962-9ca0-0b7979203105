package com.pragma.paymentservice.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.paymentservice.application.PaymentService;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.model.PaymentResponse.PaymentStatus;
import com.pragma.paymentservice.infrastructure.exception.GlobalExceptionHandler;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(GlobalExceptionHandler.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private PaymentRequest validRequest;
    private PaymentResponse successResponse;

    @BeforeEach
    void setUp() {
        validRequest = new PaymentRequest(
            "OP-20240101-001",
            "WEB",
            new BigDecimal("2500.00"),
            "9876543210",
            "Pago de crédito"
        );

        successResponse = PaymentResponse.success(
            "OP-20240101-001",
            "Pago procesado exitosamente",
            "TXN-12345"
        );
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando el pago se procesa correctamente")
    void shouldReturn200OkWhenPaymentIsProcessedSuccessfully() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.message").value("Pago procesado exitosamente"))
            .andExpect(jsonPath("$.transactionId").value("TXN-12345"));
    }

    @Test
    @DisplayName("Debe retornar 400 Bad Request cuando la solicitud es inválida")
    void shouldReturn400BadRequestWhenRequestIsInvalid() throws Exception {
        PaymentRequest invalidRequest = new PaymentRequest(
            null,
            "",
            BigDecimal.ZERO,
            null,
            null
        );

        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(
                PaymentResponse.failure("Datos de pago inválidos")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe retornar 409 Conflict cuando hay conflicto de idempotencia")
    void shouldReturn409ConflictWhenIdempotencyConflictOccurs() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.failedFuture(
                new IdempotencyException("Clave de idempotencia duplicada")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 500 Internal Server Error cuando falla el procesamiento")
    void shouldReturn500WhenProcessingFails() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.failedFuture(
                new PaymentProcessingException("Error en el procesador de pagos")
            ));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("Debe retornar 200 OK al consultar estado de pago existente")
    void shouldReturn200OkWhenCheckingStatusOfExistingPayment() throws Exception {
        String operationNumber = "OP-20240101-001";
        String channel = "WEB";

        when(paymentService.checkPaymentStatus(operationNumber, channel))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(get("/api/v1/payments/status")
                .param("operationNumber", operationNumber)
                .param("channel", channel))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found al consultar estado de pago inexistente")
    void shouldReturn404WhenCheckingStatusOfNonExistentPayment() throws Exception {
        String operationNumber = "OP-INEXISTENTE";
        String channel = "WEB";

        when(paymentService.checkPaymentStatus(operationNumber, channel))
            .thenReturn(CompletableFuture.completedFuture(
                PaymentResponse.failure("Pago no encontrado")
            ));

        mockMvc.perform(get("/api/v1/payments/status")
                .param("operationNumber", operationNumber)
                .param("channel", channel))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe validar Content-Type application/json en las respuestas")
    void shouldValidateJsonContentTypeInResponses() throws Exception {
        when(paymentService.processPayment(any(PaymentRequest.class)))
            .thenReturn(CompletableFuture.completedFuture(successResponse));

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}