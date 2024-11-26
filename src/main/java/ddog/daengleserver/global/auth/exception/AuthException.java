package ddog.daengleserver.global.auth.exception;

import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
