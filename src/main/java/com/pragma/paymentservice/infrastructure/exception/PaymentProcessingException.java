package com.pragma.paymentservice.infrastructure.exception;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentProcessingException extends RuntimeException {

    private final String operationNumber;
    private final String channel;
    private final BigDecimal amount;
    private final Instant failureTime;
    private final String errorCode;
    private final String severity;
    private final boolean retryable;

    public PaymentProcessingException(String message) {
        super(message);
        this.operationNumber = null;
        this.channel = null;
        this.amount = null;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_GENERIC_ERROR";
        this.severity = "HIGH";
        this.retryable = false;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = null;
        this.channel = null;
        this.amount = null;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_GENERIC_ERROR";
        this.severity = "HIGH";
        this.retryable = false;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_PROCESSING_ERROR";
        this.severity = "HIGH";
        this.retryable = true;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = "PAYMENT_PROCESSING_ERROR";
        this.severity = "HIGH";
        this.retryable = true;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, String errorCode) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = "HIGH";
        this.retryable = determineRetryable(errorCode);
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, 
                                       String errorCode, String severity, boolean retryable) {
        super(message);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = severity;
        this.retryable = retryable;
    }

    public PaymentProcessingException(String operationNumber, String channel, BigDecimal amount, String message, 
                                       String errorCode, String severity, boolean retryable, Throwable cause) {
        super(message, cause);
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.amount = amount;
        this.failureTime = Instant.now();
        this.errorCode = errorCode;
        this.severity = severity;
        this.retryable = retryable;
    }

    private static boolean determineRetryable(String errorCode) {
        return "PAYMENT_TIMEOUT".equals(errorCode) || 
               "PAYMENT_CONNECTION_ERROR".equals(errorCode) ||
               "PAYMENT_NETWORK_ERROR".equals(errorCode);
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getFailureTime() {
        return failureTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getSeverity() {
        return severity;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean isCritical() {
        return "CRITICAL".equals(severity);
    }

    public boolean isHighSeverity() {
        return "HIGH".equals(severity);
    }

    public boolean isMediumSeverity() {
        return "MEDIUM".equals(severity);
    }

    public boolean isLowSeverity() {
        return "LOW".equals(severity);
    }

    @Override
    public String toString() {
        return String.format("PaymentProcessingException{operationNumber='%s', channel='%s', amount=%s, errorCode='%s', severity='%s', retryable=%s, failureTime=%s, message='%s'}",
                operationNumber, channel, amount, errorCode, severity, retryable, failureTime, getMessage());
    }
}