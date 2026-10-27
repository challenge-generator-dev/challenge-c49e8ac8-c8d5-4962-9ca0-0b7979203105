package com.pragma.paymentservice.infrastructure.adapter.repository;

import com.pragma.paymentservice.infrastructure.adapter.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para operaciones CRUD sobre la entidad PaymentEntity.
 * Proporciona métodos de acceso a datos persistidos en la base de datos.
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    /**
     * Busca un pago por su clave de idempotencia compuesta.
     * @param operationNumber número de operación único
     * @param channel canal de la operación
     * @return Optional con la entidad encontrada o vacío
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.operationNumber = :operationNumber AND p.channel = :channel")
    Optional<PaymentEntity> findByOperationNumberAndChannel(
            @Param("operationNumber") String operationNumber,
            @Param("channel") String channel
    );
    
    /**
     * Verifica la existencia de un pago por su clave de idempotencia.
     * @param operationNumber número de operación
     * @param channel canal
     * @return true si existe, false en caso contrario
     */
    boolean existsByOperationNumberAndChannel(String operationNumber, String channel);
    
    /**
     * Busca pagos por estado para auditoría o monitoreo.
     * @param status estado del pago a buscar
     * @return lista de pagos con el estado especificado
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status ORDER BY p.createdAt DESC")
    java.util.List<PaymentEntity> findByStatus(@Param("status") String status);
    
    /**
     * Busca pagos realizados en un rango de fechas.
     * @param startDate fecha de inicio del rango
     * @param endDate fecha de fin del rango
     * @return lista de pagos dentro del rango
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    java.util.List<PaymentEntity> findByDateRange(
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate
    );
}