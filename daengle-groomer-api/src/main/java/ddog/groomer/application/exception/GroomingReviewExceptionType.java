package ddog.groomer.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GroomingReviewExceptionType {
    GROOMING_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "미용 리뷰가 존재하지 않음"),
    GROOMING_REVIEW_RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "미용 예약이 존재하지 않음.");

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
