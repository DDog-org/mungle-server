package ddog.payment.application.exception;


import ddog.payment.application.exception.common.CustomRuntimeException;

public class PaymentException extends CustomRuntimeException {
    public PaymentException(PaymentExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}