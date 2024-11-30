package ddog.user.application.exception;

import ddog.user.application.exception.common.CustomRuntimeException;

public class UserException extends CustomRuntimeException {
    public UserException(UserExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
