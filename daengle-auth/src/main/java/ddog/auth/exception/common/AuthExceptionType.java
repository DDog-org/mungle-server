package ddog.auth.exception.common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthExceptionType {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 401, "유효하지 않은 토큰"),
    UNAVAILABLE_TOKEN(HttpStatus.UNAUTHORIZED, 401, "토큰 유효성 검증 실패"),
    UNAVAILABLE_ROLE(HttpStatus.UNAUTHORIZED, 401, "권한 부족"),
    RESPONSE_CODE_ERROR(HttpStatus.BAD_REQUEST, 400,"잘못된 접근"),
    FAILED_TO_RETRIEVE_KAKAO_USER_INFO(HttpStatus.BAD_REQUEST, 400,"카카오 계정 정보 유효하지 않음"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "토큰 만료"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "지원하지 않은 토큰"),
    UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED, 401, "비식별 토큰");

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
