package ddog.groomer.application.exception;

import ddog.groomer.application.exception.common.CustomRuntimeException;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
