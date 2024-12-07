package ddog.user.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class ReservationException extends CustomRuntimeException {
    public ReservationException(ReservationExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
