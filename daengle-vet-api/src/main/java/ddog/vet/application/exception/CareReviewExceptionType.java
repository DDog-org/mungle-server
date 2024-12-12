package ddog.vet.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CareReviewExceptionType {
    CARE_REVIEW_RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "진료 예약이 존재하지 않음.");

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
