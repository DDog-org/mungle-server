package ddog.daengleserver.implementation;

import ddog.daengleserver.global.exception.CustomRuntimeException;
import ddog.daengleserver.implementation.enums.AccountExceptionType;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
