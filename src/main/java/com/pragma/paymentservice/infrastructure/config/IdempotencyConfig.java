package com.pragma.paymentservice.infrastructure.config;

import com.pragma.paymentservice.infrastructure.adapter.entity.IdempotencyEntity;
import com.pragma.paymentservice.infrastructure.adapter.repository.IdempotencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class IdempotencyConfig {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyConfig.class);
    private static final int DEFAULT_WINDOW_HOURS = 24;

    private final IdempotencyRepository idempotencyRepository;
    private final int windowHours;

    public IdempotencyConfig(
            IdempotencyRepository idempotencyRepository,
            @Value("${payment.idempotency.window-hours:24}") int windowHours) {
        this.idempotencyRepository = idempotencyRepository;
        this.windowHours = windowHours > 0 ? windowHours : DEFAULT_WINDOW_HOURS;
    }

    @Transactional
    public boolean isRequestIdempotent(String idempotencyKey) {
        log.debug("Verificando idempotencia para clave: {}", idempotencyKey);

        Optional<IdempotencyEntity> existing = idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isEmpty()) {
            log.debug("Clave de idempotencia no encontrada, es nueva: {}", idempotencyKey);
            return false;
        }

        IdempotencyEntity record = existing.get();

        if (isExpired(record)) {
            log.info("Clave de idempotencia expirada, permite nueva solicitud: {}", idempotencyKey);
            idempotencyRepository.delete(record);
            return false;
        }

        log.info("Clave de idempotencia válida encontrada. Clave: {}, Expira en: {} horas", 
                idempotencyKey, calculateRemainingHours(record));
        return true;
    }

    @Transactional
    public void recordIdempotentRequest(String idempotencyKey, String operationNumber, String channel) {
        log.info("Registrando clave de idempotencia: {} para operación: {}", 
                idempotencyKey, operationNumber);

        if (idempotencyRepository.existsByIdempotencyKey(idempotencyKey)) {
            log.warn("La clave de idempotencia ya existe: {}", idempotencyKey);
            return;
        }

        IdempotencyEntity entity = new IdempotencyEntity();
        entity.setIdempotencyKey(idempotencyKey);
        entity.setOperationNumber(operationNumber);
        entity.setChannel(channel);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setExpiresAt(LocalDateTime.now().plusHours(windowHours));

        idempotencyRepository.save(entity);
        log.info("Clave de idempotencia registrada exitosamente. Expira en {} horas", windowHours);
    }

    @Transactional
    public Optional<IdempotencyEntity> getIdempotencyRecord(String idempotencyKey) {
        return idempotencyRepository.findByIdempotencyKey(idempotencyKey);
    }

    @Transactional
    public void cleanupExpiredRecords() {
        log.info("Iniciando limpieza de registros de idempotencia expirados");
        int deleted = idempotencyRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        log.info("Registros de idempotencia expirados eliminados: {}", deleted);
    }

    public int getWindowHours() {
        return windowHours;
    }

    private boolean isExpired(IdempotencyEntity record) {
        return LocalDateTime.now().isAfter(record.getExpiresAt());
    }

    private long calculateRemainingHours(IdempotencyEntity record) {
        Duration duration = Duration.between(LocalDateTime.now(), record.getExpiresAt());
        return Math.max(0, duration.toHours());
    }
}