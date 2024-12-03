package ddog.auth.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
