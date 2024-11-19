package ddog.daengleserver.global.auth.exception;

import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
