package ddog.user.application.exception.estimate;

import ddog.auth.exception.common.CustomRuntimeException;

public class GroomingEstimateException extends CustomRuntimeException {
    public GroomingEstimateException(GroomingEstimateExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
