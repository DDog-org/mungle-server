package ddog.user.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class GroomerException extends CustomRuntimeException {
    public GroomerException(GroomerExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
