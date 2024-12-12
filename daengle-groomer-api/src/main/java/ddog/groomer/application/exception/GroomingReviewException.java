package ddog.groomer.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class GroomingReviewException extends CustomRuntimeException {
    public GroomingReviewException(GroomingReviewExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
