package ddog.daengleserver.domain.exception;

import ddog.daengleserver.domain.enums.OrderExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class OrderException extends CustomRuntimeException {
    public OrderException(OrderExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
