package com.pragma.paymentservice.domain.port.out;

import com.pragma.paymentservice.domain.model.Payment;
import java.util.Optional;

/**
 * Puerto de salida que define el contrato para la persistencia de pagos.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 */
public interface PaymentPersistencePort {
    
    /**
     * Guarda un pago en el sistema de persistencia.
     * @param payment la entidad de dominio con los datos del pago
     * @return el pago persistido con su identificador asignado
     */
    Payment save(Payment payment);
    
    /**
     * Busca un pago por su clave de idempotencia.
     * @param operationNumber número de operación único
     * @param channel canal por el cual se realizó la operación
     * @return un Optional con el pago encontrado o vacío si no existe
     */
    Optional<Payment> findByIdempotencyKey(String operationNumber, String channel);
    
    /**
     * Busca un pago por su identificador interno.
     * @param id identificador del pago
     * @return un Optional con el pago encontrado o vacío si no existe
     */
    Optional<Payment> findById(Long id);
    
    /**
     * Actualiza el estado de un pago existente.
     * @param payment el pago con los datos actualizados
     * @return el pago actualizado
     */
    Payment update(Payment payment);
    
    /**
     * Verifica si existe un pago con la clave de idempotencia proporcionada.
     * @param operationNumber número de operación
     * @param channel canal de la operación
     * @return true si existe un pago con esa clave, false en caso contrario
     */
    boolean existsByIdempotencyKey(String operationNumber, String channel);
}