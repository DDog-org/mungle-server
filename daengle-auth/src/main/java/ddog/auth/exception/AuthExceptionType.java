package ddog.auth.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthExceptionType {
    UNAVAILABLE_TOKEN(HttpStatus.BAD_REQUEST, 400, "토큰 유효성 검증 실패"),
    UNAVAILABLE_ROLE(HttpStatus.BAD_REQUEST, 400, "권한 부족"),
    RESPONSE_CODE_ERROR(HttpStatus.BAD_REQUEST, 400,"잘못된 접근"),
    FAILED_TO_RETRIEVE_KAKAO_USER_INFO(HttpStatus.BAD_REQUEST, 400,"카카오 계정 정보 유효하지 않음");

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
