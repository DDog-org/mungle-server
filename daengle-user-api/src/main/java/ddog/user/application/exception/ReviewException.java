package ddog.user.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class ReviewException extends CustomRuntimeException {

    public ReviewException(ReviewExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }

    public ReviewException(ReviewExceptionType type, String banWord, Object... args) {
        super(banWord, type.getHttpStatus(), type.getCode());
    }
}
