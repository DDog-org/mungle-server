package ddog.daengleserver.implementation.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AccountExceptionType {
    ACCOUNT_EXCEPTION_TYPE(HttpStatus.NOT_FOUND, 404, "유저를 찾을 수 없음"),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, 404, "유저를 찾을 수 없음"),;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
