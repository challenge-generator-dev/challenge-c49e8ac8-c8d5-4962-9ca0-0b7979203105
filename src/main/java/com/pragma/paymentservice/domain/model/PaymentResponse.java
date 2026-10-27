package com.pragma.paymentservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa la respuesta del procesamiento de una solicitud de pago.
 * Contiene información sobre el estado del pago y los detalles resultantes.
 */
public record PaymentResponse(
    UUID paymentId,

    String operationNumber,

    String channel,

    PaymentStatus status,

    BigDecimal amount,

    String currency,

    LocalDateTime processedAt,

    String reference,

    String message,

    String transactionId,

    String idempotencyKey
) {
    /**
     * Enumeración que representa los posibles estados de un pago.
     */
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REJECTED,
        TIMEOUT
    }

    /**
     * Constructor para respuestas exitosas.
     * @param paymentId ID único del pago generado
     * @param operationNumber Número de operación original
     * @param channel Canal original
     * @param amount Monto procesado
     * @param currency Moneda procesada
     * @param reference Referencia original
     * @param transactionId ID de transacción generado por el core bancario
     * @param idempotencyKey Clave de idempotencia
     * @return PaymentResponse con estado COMPLETED
     */
    public static PaymentResponse success(
        UUID paymentId,
        String operationNumber,
        String channel,
        BigDecimal amount,
        String currency,
        String reference,
        String transactionId,
        String idempotencyKey
    ) {
        return new PaymentResponse(
            paymentId,
            operationNumber,
            channel,
            PaymentStatus.COMPLETED,
            amount,
            currency,
            LocalDateTime.now(),
            reference,
            "Pago procesado exitosamente",
            transactionId,
            idempotencyKey
        );
    }

    /**
     * Constructor para respuestas fallidas.
     * @param operationNumber Número de operación original
     * @param channel Canal original
     * @param status Estado del pago (FAILED, REJECTED, TIMEOUT)
     * @param message Mensaje descriptivo del error
     * @param idempotencyKey Clave de idempotencia
     * @return PaymentResponse con el estado especificado
     */
    public static PaymentResponse failure(
        String operationNumber,
        String channel,
        PaymentStatus status,
        String message,
        String idempotencyKey
    ) {
        return new PaymentResponse(
            null,
            operationNumber,
            channel,
            status,
            null,
            null,
            LocalDateTime.now(),
            null,
            message,
            null,
            idempotencyKey
        );
    }
}