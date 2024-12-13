package ddog.user.application.exception.info;

import ddog.auth.exception.common.CustomRuntimeException;

public class BeautyShopException extends CustomRuntimeException {
    public BeautyShopException(BeautyShopExceptionType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
