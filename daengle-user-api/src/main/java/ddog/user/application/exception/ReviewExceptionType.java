package ddog.user.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReviewExceptionType {
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, 404, "리뷰 데이터 없음"),
    REVIEW_HAS_WRITTEN(HttpStatus.BAD_REQUEST, 404, "이미 작성된 리뷰"),
    REVIEW_INVALID_SERVICE_TYPE(HttpStatus.BAD_REQUEST, 404, "예약 내역의 서비스 타입과 불일치"),
    REVIEWWEE_NOT_FOUNT(HttpStatus.BAD_REQUEST, 404, "리뷰 대상자가 존재하지 않음"),
    REVIEW_CONTENT_CONTAIN_BAN_WORD(HttpStatus.BAD_REQUEST, 4000, "리뷰 내용에 금칙어 포함");

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
