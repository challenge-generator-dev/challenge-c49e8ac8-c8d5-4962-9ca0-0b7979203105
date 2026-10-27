package com.pragma.paymentservice.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una solicitud de pago emitida por un cliente o sistema.
 * Contiene toda la información necesaria para procesar el pago y garantizar su idempotencia.
 */
public record PaymentRequest(
    @NotNull(message = "El ID de la operación es obligatorio")
    @Size(min = 1, max = 64, message = "El ID de la operación debe tener entre 1 y 64 caracteres")
    String operationNumber,

    @NotNull(message = "El canal es obligatorio")
    @Size(min = 1, max = 32, message = "El canal debe tener entre 1 y 32 caracteres")
    String channel,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La moneda debe ser un código de 3 caracteres")
    String currency,

    @NotNull(message = "El ID del cliente es obligatorio")
    UUID customerId,

    @NotNull(message = "El ID de la cuenta origen es obligatorio")
    UUID sourceAccountId,

    @NotNull(message = "El ID de la cuenta destino es obligatorio")
    UUID destinationAccountId,

    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 255, message = "La referencia no puede exceder 255 caracteres")
    String reference,

    @NotNull(message = "La marca de tiempo es obligatoria")
    LocalDateTime timestamp,

    @Size(max = 255, message = "Los metadatos no pueden exceder 255 caracteres")
    String metadata
) {
    /**
     * Genera una clave de idempotencia única para esta solicitud.
     * La clave se compone del número de operación y el canal, separados por un guión.
     * @return Clave de idempotencia en formato "operationNumber-channel"
     */
    public String generateIdempotencyKey() {
        return operationNumber + "-" + channel;
    }

    /**
     * Valida que la solicitud cumpla con las reglas de negocio básicas.
     * @throws IllegalArgumentException si alguna validación falla
     */
    public void validate() {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (timestamp.isAfter(LocalDateTime.now().plusMinutes(5))) {
            throw new IllegalArgumentException("La marca de tiempo no puede ser futura");
        }
        if (timestamp.isBefore(LocalDateTime.now().minusDays(1))) {
            throw new IllegalArgumentException("La marca de tiempo no puede tener más de 24 horas");
        }
    }
}