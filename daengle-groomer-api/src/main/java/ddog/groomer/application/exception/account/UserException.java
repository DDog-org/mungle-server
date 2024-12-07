package ddog.groomer.application.exception.account;

import ddog.auth.exception.common.CustomRuntimeException;

public class UserException extends CustomRuntimeException {
    public UserException(UserExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
