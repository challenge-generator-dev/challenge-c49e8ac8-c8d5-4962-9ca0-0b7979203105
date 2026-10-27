package com.pragma.paymentservice.infrastructure.exception;

import java.time.Instant;

public class IdempotencyException extends RuntimeException {
    
    private final String operationNumber;
    private final String channel;
    private final Instant attemptTime;
    private final String reasonCode;

    public IdempotencyException(String message) {
        super(message);
        this.operationNumber = null;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_GENERIC";
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = null;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_GENERIC";
    }

    public IdempotencyException(String operationNumber, String channel, String message) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_DUPLICATE_KEY";
    }

    public IdempotencyException(String operationNumber, String channel, String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = null;
        this.attemptTime = Instant.now();
        this.reasonCode = "IDEMP_DUPLICATE_KEY";
    }

    public IdempotencyException(String operationNumber, String channel, String message, String reasonCode) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = reasonCode;
    }

    public IdempotencyException(String operationNumber, String channel, String message, String reasonCode, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.attemptTime = Instant.now();
        this.reasonCode = reasonCode;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public Instant getAttemptTime() {
        return attemptTime;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getIdempotencyKey() {
        if (operationNumber != null && channel != null) {
            return operationNumber + "_" + channel;
        }
        return null;
    }

    public boolean isDuplicateKeyError() {
        return "IDEMP_DUPLICATE_KEY".equals(reasonCode);
    }

    public boolean isExpiredKeyError() {
        return "IDEMP_EXPIRED_KEY".equals(reasonCode);
    }

    public boolean isInvalidKeyError() {
        return "IDEMP_INVALID_KEY".equals(reasonCode);
    }

    @Override
    public String toString() {
        return String.format("IdempotencyException{operationNumber='%s', channel='%s', reasonCode='%s', attemptTime=%s, message='%s'}",
                operationNumber, channel, reasonCode, attemptTime, getMessage());
    }
}