package ddog.payment.exception;


import ddog.payment.exception.common.CustomRuntimeException;

public class PaymentException extends CustomRuntimeException {
    public PaymentException(PaymentExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}