package com.pragma.paymentservice.infrastructure.controller;

import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.in.PaymentServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Pagos", description = "Endpoints para la gestión de solicitudes de pago")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServicePort paymentServicePort;

    public PaymentController(PaymentServicePort paymentServicePort) {
        this.paymentServicePort = paymentServicePort;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Procesar pago", description = "Procesa una solicitud de pago con soporte para idempotencia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto de idempotencia - operación duplicada", content = @Content),
        @ApiResponse(responseCode = "422", description = "Error de procesamiento del pago", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest paymentRequest) {
        logger.info("Recibida solicitud de pago con idempotencyKey: {}", 
            paymentRequest.generateIdempotencyKey());
        paymentRequest.validate();
        
        PaymentResponse response = paymentServicePort.processPayment(paymentRequest)
            .whenComplete((result, error) -> {
                if (error != null) {
                    logger.error("Error al procesar pago: {}", error.getMessage());
                } else {
                    logger.info("Pago procesado exitosamente: {}", result);
                }
            })
            .join();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/status/{operationNumber}/{channel}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Consultar estado del pago", description = "Consulta el estado de una operación de pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado del pago consultado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<PaymentResponse> checkPaymentStatus(
            @Parameter(description = "Número de operación única", required = true)
            @PathVariable String operationNumber,
            @Parameter(description = "Canal de la operación", required = true)
            @PathVariable String channel) {
        logger.info("Consultando estado del pago: operationNumber={}, channel={}", operationNumber, channel);
        
        PaymentResponse response = paymentServicePort.checkPaymentStatus(operationNumber, channel)
            .whenComplete((result, error) -> {
                if (error != null) {
                    logger.error("Error al consultar estado: {}", error.getMessage());
                } else {
                    logger.info("Estado consultado: {}", result);
                }
            })
            .join();
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Health check", description = "Verifica la disponibilidad del servicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio disponible",
            content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }
}