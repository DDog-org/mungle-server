package ddog.groomer.application.exception;

import ddog.groomer.application.exception.common.CustomRuntimeException;

public class GroomerException extends CustomRuntimeException {
    public GroomerException(GroomerExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}