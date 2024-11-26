package ddog.daengleserver.domain.exception;

import ddog.daengleserver.domain.enums.UserExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class UserException extends CustomRuntimeException {
    public UserException(UserExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
