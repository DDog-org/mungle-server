package ddog.exception;


import ddog.exception.common.CustomRuntimeException;

public class PaymentException extends CustomRuntimeException {
    public PaymentException(PaymentExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}