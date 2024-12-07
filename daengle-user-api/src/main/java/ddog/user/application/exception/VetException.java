package ddog.user.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class VetException extends CustomRuntimeException {
    public VetException(VetExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
