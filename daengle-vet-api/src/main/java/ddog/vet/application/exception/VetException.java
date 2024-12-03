package ddog.vet.application.exception;

import ddog.vet.application.exception.common.CustomRuntimeException;

public class VetException extends CustomRuntimeException {
    public VetException(VetExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
