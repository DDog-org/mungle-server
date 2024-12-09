package ddog.groomer.application.exception.account;


import ddog.auth.exception.common.CustomRuntimeException;

public class AccountException extends CustomRuntimeException {
    public AccountException(AccountExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
