package ddog.payment.exception;


import ddog.payment.exception.common.CustomRuntimeException;

public class OrderException extends CustomRuntimeException {
    public OrderException(OrderExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
