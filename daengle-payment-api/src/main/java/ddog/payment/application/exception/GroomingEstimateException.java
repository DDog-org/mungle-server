package ddog.payment.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class GroomingEstimateException extends CustomRuntimeException {
    public GroomingEstimateException(GroomingEstimateExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
