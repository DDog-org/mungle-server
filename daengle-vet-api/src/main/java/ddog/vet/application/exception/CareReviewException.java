package ddog.vet.application.exception;


import ddog.auth.exception.common.CustomRuntimeException;

public class CareReviewException extends CustomRuntimeException {
    public CareReviewException(CareReviewExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
