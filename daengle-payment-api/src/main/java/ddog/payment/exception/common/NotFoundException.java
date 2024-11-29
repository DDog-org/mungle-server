package ddog.payment.exception.common;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomRuntimeException {
    public NotFoundException(String message, Object... args) {
        super(message, HttpStatus.NOT_FOUND, null);
    }
}
