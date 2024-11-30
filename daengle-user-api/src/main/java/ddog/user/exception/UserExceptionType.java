package ddog.user.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserExceptionType {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "사용자가 존재하지 않음.");

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
