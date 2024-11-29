package ddog.exception;

import ddog.daengleserver.domain.enums.UserExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class UserException extends CustomRuntimeException {
    public UserException(UserExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
