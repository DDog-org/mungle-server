package ddog.user.exception;

import ddog.user.exception.common.CustomRuntimeException;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
