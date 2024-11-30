package ddog.user.application.exception;

import ddog.user.application.exception.common.CustomRuntimeException;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
