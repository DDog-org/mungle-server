package ddog.auth.exception.common;

public class AuthException extends CustomRuntimeException {
    public AuthException(AuthExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
