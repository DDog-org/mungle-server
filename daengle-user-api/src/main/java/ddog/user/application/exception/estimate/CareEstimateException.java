package ddog.user.application.exception.estimate;

import ddog.auth.exception.common.CustomRuntimeException;

public class CareEstimateException extends CustomRuntimeException {
    public CareEstimateException(CareEstimateExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
