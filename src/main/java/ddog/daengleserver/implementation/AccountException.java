package ddog.daengleserver.implementation;

import ddog.daengleserver.global.exception.CustomRuntimeException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
