package ddog.daengleserver.domain.exception;

import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class PaymentException extends CustomRuntimeException {
    public PaymentException(PaymentExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}