package ddog.mungleserver.implementation;

import ddog.mungleserver.global.exception.CustomRuntimeException;
import ddog.mungleserver.implementation.enums.CustomerExceptionType;

public class CustomerException extends CustomRuntimeException {
    public CustomerException(CustomerExceptionType message, Object... args) {
        super(String.valueOf(message), args);
    }
}
