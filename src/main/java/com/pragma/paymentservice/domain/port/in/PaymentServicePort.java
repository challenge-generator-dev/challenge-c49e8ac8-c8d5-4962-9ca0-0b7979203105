package com.pragma.paymentservice.domain.port.in;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import jakarta.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * Puerto de entrada que define las operaciones disponibles para el servicio de pagos.
 * Esta interfaz actúa como contrato entre la capa de aplicación y la capa de dominio.
 */
public interface PaymentServicePort {

    /**
     * Procesa una solicitud de pago verificando su idempotencia y aplicando las reglas de negocio.
     * @param paymentRequest Solicitud de pago validada
     * @return CompletableFuture con la respuesta del procesamiento
     * @throws jakarta.validation.ConstraintViolationException si la solicitud no es válida
     * @throws com.pragma.paymentservice.infrastructure.exception.IdempotencyException si ya existe un pago con la misma clave de idempotencia
     * @throws com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException si ocurre un error durante el procesamiento
     */
    CompletableFuture<PaymentResponse> processPayment(@Valid PaymentRequest paymentRequest);

    /**
     * Verifica el estado de un pago previamente procesado.
     * @param operationNumber Número de operación
     * @param channel Canal de origen
     * @return CompletableFuture con la respuesta del pago si existe, o null si no existe
     */
    CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel);
}