package ddog.daengleserver.implementation;

import ddog.daengleserver.global.exception.CustomRuntimeException;
import ddog.daengleserver.implementation.enums.CustomerExceptionType;

public class CustomerException extends CustomRuntimeException {
    public CustomerException(CustomerExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
