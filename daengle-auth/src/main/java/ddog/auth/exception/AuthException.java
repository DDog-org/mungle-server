package ddog.auth.exception;

import ddog.auth.exception.enums.AuthExceptionType;
import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
