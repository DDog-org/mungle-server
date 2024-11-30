package ddog.user.exception;

import ddog.user.exception.common.CustomRuntimeException;

public class UserException extends CustomRuntimeException {
    public UserException(UserExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
