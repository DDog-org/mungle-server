package ddog.user.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GroomerExceptionType {
    INVALID_REQUEST_DATA_FORMAT(HttpStatus.BAD_REQUEST, 400, "데이터 형식 오류"),
    GROOMER_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "미용사가 존재하지 않음.");

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
