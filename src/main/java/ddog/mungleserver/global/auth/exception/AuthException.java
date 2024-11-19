package ddog.mungleserver.global.auth.exception;

import ddog.mungleserver.global.auth.exception.enums.AuthExceptionType;
import ddog.mungleserver.global.exception.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
