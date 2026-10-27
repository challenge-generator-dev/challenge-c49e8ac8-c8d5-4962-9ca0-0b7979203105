package com.pragma.paymentservice.application;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.Payment;
import com.pragma.paymentservice.domain.model.PaymentRequest;
import com.pragma.paymentservice.domain.model.PaymentResponse;
import com.pragma.paymentservice.domain.port.in.PaymentServicePort;
import com.pragma.paymentservice.domain.port.out.PaymentPersistencePort;
import com.pragma.paymentservice.infrastructure.exception.IdempotencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementación del caso de uso que orquesta la lógica de negocio del servicio de pagos.
 * Coordina la validación de idempotencia, la persistencia y la construcción de respuestas.
 */
@Service
public class PaymentService implements PaymentServicePort {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private static final int IDEMPOTENCY_WINDOW_HOURS = 24;
    
    private final PaymentPersistencePort persistencePort;
    
    public PaymentService(PaymentPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }
    
    @Override
    @Transactional
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest paymentRequest) {
        logger.info("Iniciando procesamiento de pago con clave de idempotencia: {}", 
                paymentRequest.generateIdempotencyKey());
        
        paymentRequest.validate();
        
        String operationNumber = paymentRequest.operationNumber();
        String channel = paymentRequest.channel();
        
        Optional<Payment> existingPayment = persistencePort.findByIdempotencyKey(operationNumber, channel);
        
        if (existingPayment.isPresent()) {
            logger.info("Pago encontrado para clave de idempotencia: {}, retornando respuesta existente",
                    paymentRequest.generateIdempotencyKey());
            return CompletableFuture.completedFuture(buildResponseFromPayment(existingPayment.get()));
        }
        
        validateIdempotencyWindow(paymentRequest.createdAt());
        
        try {
            Payment payment = buildPaymentFromRequest(paymentRequest);
            payment.setStatus(PaymentResponse.PaymentStatus.PROCESSING.name());
            
            Payment savedPayment = persistencePort.save(payment);
            logger.info("Pago guardado exitosamente con ID: {}", savedPayment.getId());
            
            Payment completedPayment = processPaymentWithExternalSystems(savedPayment);
            
            Payment finalPayment = persistencePort.update(completedPayment);
            
            logger.info("Pago completado exitosamente con número de operación: {}", 
                    finalPayment.getOperationNumber());
            
            return CompletableFuture.completedFuture(buildResponseFromPayment(finalPayment));
            
        } catch (Exception e) {
            logger.error("Error al procesar pago para operación: {}", operationNumber, e);
            return CompletableFuture.completedFuture(
                    PaymentResponse.failure("Error al procesar el pago: " + e.getMessage())
            );
        }
    }
    
    @Override
    public CompletableFuture<PaymentResponse> checkPaymentStatus(String operationNumber, String channel) {
        logger.info("Consultando estado del pago para operación: {}, canal: {}", operationNumber, channel);
        
        Optional<Payment> payment = persistencePort.findByIdempotencyKey(operationNumber, channel);
        
        if (payment.isEmpty()) {
            logger.warn("No se encontró pago para operación: {}, canal: {}", operationNumber, channel);
            return CompletableFuture.completedFuture(
                    PaymentResponse.failure("Pago no encontrado para los parámetros proporcionados")
            );
        }
        
        return CompletableFuture.completedFuture(buildResponseFromPayment(payment.get()));
    }
    
    private void validateIdempotencyWindow(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusHours(IDEMPOTENCY_WINDOW_HOURS);
        
        if (createdAt.isBefore(windowStart)) {
            throw new IdempotencyException(
                    "La clave de idempotencia ha expirado. El máximo es de " + IDEMPOTENCY_WINDOW_HOURS + " horas"
            );
        }
    }
    
    private Payment buildPaymentFromRequest(PaymentRequest request) {
        return Payment.builder()
                .operationNumber(request.operationNumber())
                .channel(request.channel())
                .amount(request.amount())
                .currency(request.currency())
                .customerId(request.customerId())
                .customerName(request.customerName())
                .customerEmail(request.customerEmail())
                .customerPhone(request.customerPhone())
                .description(request.description())
                .status(PaymentResponse.PaymentStatus.PENDING.name())
                .createdAt(request.createdAt())
                .build();
    }
    
    private PaymentResponse buildResponseFromPayment(Payment payment) {
        return PaymentResponse.success(
                payment.getId(),
                payment.getOperationNumber(),
                payment.getChannel(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
    
    private Payment processPaymentWithExternalSystems(Payment payment) {
        logger.debug("Procesando pago con sistemas externos - ID: {}", payment.getId());
        
        boolean fraudCheckPassed = performFraudCheck(payment);
        
        if (!fraudCheckPassed) {
            payment.setStatus(PaymentResponse.PaymentStatus.REJECTED.name());
            payment.setFailureReason("Transacción rechazada por motor antifraude");
            return payment;
        }
        
        boolean coreBankingApproved = routeToCoreBanking(payment);
        
        if (!coreBankingApproved) {
            payment.setStatus(PaymentResponse.PaymentStatus.FAILED.name());
            payment.setFailureReason("Rechazo del core bancario");
            return payment;
        }
        
        payment.setStatus(PaymentResponse.PaymentStatus.APPROVED.name());
        payment.setAuthorizationCode(generateAuthorizationCode());
        
        return payment;
    }
    
    private boolean performFraudCheck(Payment payment) {
        logger.info("Ejecutando verificación antifraude para cliente: {}", payment.getCustomerId());
        return true;
    }
    
    private boolean routeToCoreBanking(Payment payment) {
        logger.info("Enviando solicitud al core bancario para operación: {}", payment.getOperationNumber());
        return true;
    }
    
    private String generateAuthorizationCode() {
        return "AUTH-" + System.currentTimeMillis();
    }
}