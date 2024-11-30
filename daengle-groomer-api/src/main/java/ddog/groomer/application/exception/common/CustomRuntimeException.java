package ddog.groomer.application.exception.common;


import org.springframework.http.HttpStatus;

public abstract class CustomRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final Integer code;

    public CustomRuntimeException(String message, HttpStatus httpStatus, Integer code) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }
}