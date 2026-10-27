package com.pragma.paymentservice.infrastructure.adapter;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import com.pragma.paymentservice.infrastructure.adapter.entity.PaymentEntity;
import com.pragma.paymentservice.infrastructure.adapter.repository.PaymentRepository;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import com.pragma.paymentservice.infrastructure.exception.PaymentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class PaymentJpaAdapter implements PaymentPersistencePort {

    private static final Logger log = LoggerFactory.getLogger(PaymentJpaAdapter.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_PROCESSING = "PROCESSING";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_FAILED = "FAILED";
    private static final String STATUS_DUPLICATE = "DUPLICATE";

    private final PaymentRepository paymentRepository;

    public PaymentJpaAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String idempotencyKey = request.generateIdempotencyKey();
                log.info("Procesando pago con clave de idempotencia: {}", idempotencyKey);

                Optional<PaymentEntity> existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
                if (existingPayment.isPresent()) {
                    log.info("Pago duplicado detectado para clave: {}", idempotencyKey);
                    return buildResponseFromEntity(existingPayment.get(), true);
                }

                PaymentEntity entity = new PaymentEntity();
                entity.setIdempotencyKey(idempotencyKey);
                entity.setOperationNumber(request.operationNumber());
                entity.setChannel(request.channel());
                entity.setAmount(request.amount());
                entity.setCurrency(request.currency());
                entity.setStatus(STATUS_PROCESSING);
                entity.setCustomerId(request.customerId());
                entity.setMerchantId(request.merchantId());
                entity.setDescription(request.description());

                PaymentEntity savedEntity = paymentRepository.save(entity);
                log.info("Pago guardado con ID: {}", savedEntity.getId());

                PaymentResponse response = simulatePaymentProcessing(savedEntity);

                savedEntity.setStatus(response.status().name());
                savedEntity.setResponseData(serializeResponse(response));
                paymentRepository.save(savedEntity);

                log.info("Pago procesado exitosamente. OperationNumber: {}, Status: {}", 
                        savedEntity.getOperationNumber(), savedEntity.getStatus());

                return response;
            } catch (IdempotencyException e) {
                log.error("Error de idempotencia: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("Error al procesar pago: {}", e.getMessage(), e);
                throw new PaymentProcessingException("Error al procesar el pago: " + e.getMessage(), e);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Consultando estado del pago. OperationNumber: {}, Channel: {}", operationNumber, channel);

            Optional<PaymentEntity> entity = paymentRepository
                    .findByOperationNumberAndChannel(operationNumber, channel);

            if (entity.isEmpty()) {
                log.warn("Pago no encontrado. OperationNumber: {}, Channel: {}", operationNumber, channel);
                return PaymentResponse.failure("PAGO_NO_ENCONTRADO", 
                        "No se encontró el pago para el número de operación y canal especificado");
            }

            PaymentEntity payment = entity.get();
            log.info("Estado del pago encontrado. ID: {}, Status: {}", payment.getId(), payment.getStatus());

            return buildResponseFromEntity(payment, false);
        });
    }

    private PaymentResponse simulatePaymentProcessing(PaymentEntity entity) {
        boolean isHighAmount = entity.getAmount().compareTo(new BigDecimal("10000")) > 0;
        boolean isFraudRisk = entity.getAmount().compareTo(new BigDecimal("50000")) > 0;

        if (isFraudRisk) {
            log.warn("Pago marcado como riesgo de fraude. Amount: {}", entity.getAmount());
            return PaymentResponse.failure("RIESGO_FRAUDE", 
                    "El pago ha sido flagged por el motor antifraude");
        }

        if (isHighAmount) {
            log.info("Monto alto detectado, requiere aprobación manual. Amount: {}", entity.getAmount());
            return PaymentResponse.builder()
                    .operationNumber(entity.getOperationNumber())
                    .status(PaymentResponse.PaymentStatus.PENDING)
                    .message("Pago en espera de aprobación por monto alto")
                    .idempotencyKey(entity.getIdempotencyKey())
                    .build();
        }

        return PaymentResponse.success(
                entity.getOperationNumber(),
                entity.getAmount(),
                entity.getCurrency(),
                "Pago procesado exitosamente"
        );
    }

    private PaymentResponse buildResponseFromEntity(PaymentEntity entity, boolean isDuplicate) {
        if (isDuplicate || STATUS_DUPLICATE.equals(entity.getStatus())) {
            return PaymentResponse.builder()
                    .operationNumber(entity.getOperationNumber())
                    .status(PaymentResponse.PaymentStatus.DUPLICATE)
                    .message("Pago duplicado - se retorna la respuesta original")
                    .idempotencyKey(entity.getIdempotencyKey())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }

        PaymentResponse.PaymentStatus status = PaymentResponse.PaymentStatus.valueOf(entity.getStatus());
        return PaymentResponse.builder()
                .operationNumber(entity.getOperationNumber())
                .status(status)
                .message(getStatusMessage(status))
                .idempotencyKey(entity.getIdempotencyKey())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private String getStatusMessage(PaymentResponse.PaymentStatus status) {
        return switch (status) {
            case COMPLETED -> "Pago completado exitosamente";
            case FAILED -> "Pago fallido";
            case PENDING -> "Pago en pendiente";
            case PROCESSING -> "Pago en procesamiento";
            case DUPLICATE -> "Pago duplicado";
            default -> "Estado desconocido";
        };
    }

    private String serializeResponse(PaymentResponse response) {
        return String.format("{\"status\":\"%s\",\"message\":\"%s\"}", 
                response.status(), response.message());
    }
}