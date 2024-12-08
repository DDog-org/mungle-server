package ddog.user.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReviewExceptionType {
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, 404, "리뷰 데이터 없음"),
    REVIEWWEE_NOT_FOUNT(HttpStatus.BAD_REQUEST, 404, "리뷰 대상자가 존재하지 않음");

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
