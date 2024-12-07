package ddog.vet.application.exception.account;

import ddog.auth.exception.common.CustomRuntimeException;

public class PetException extends CustomRuntimeException {
    public PetException(PetExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
