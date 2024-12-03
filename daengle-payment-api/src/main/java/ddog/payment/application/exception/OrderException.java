package ddog.payment.application.exception;


import ddog.auth.exception.common.CustomRuntimeException;

public class OrderException extends CustomRuntimeException {
    public OrderException(OrderExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
