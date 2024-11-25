package ddog.daengleserver.domain.exception;

import ddog.daengleserver.domain.enums.PaymentExceptionType;
import ddog.daengleserver.global.exception.CustomRuntimeException;

public class PaymentException extends CustomRuntimeException {
    public PaymentException(PaymentExceptionType message, Object... args) {super(String.valueOf(message), args);}
}
