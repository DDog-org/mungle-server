package ddog.auth.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthExceptionType {
    /* 토큰 관련 오류 */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1001, "토큰이 위조되거나 변조되었습니다."),
    INVALID_TOKEN_STRUCTURE(HttpStatus.BAD_REQUEST, 1002, "유효하지 않은 토큰 형식입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1003, "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 1004, "지원하지 않는 토큰 형식입니다."),
    UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED, 1005, "알 수 없는 토큰입니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "Authorization 헤더가 누락되었습니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, 1007, "토큰 값이 비어 있습니다."),
    MISSING_AUTH_CLAIM(HttpStatus.FORBIDDEN, 1008, "토큰에 권한 정보가 없습니다."),
    MISSING_COOKIE_TOKEN(HttpStatus.BAD_REQUEST, 1009, "쿠키에 토큰 정보가 없습니다"),



    /* 권한 관련 오류 */
    UNAVAILABLE_ROLE(HttpStatus.FORBIDDEN, 1101, "접근할 권한이 없습니다."),

    /* 요청 및 응답 오류 */
    RESPONSE_CODE_ERROR(HttpStatus.BAD_REQUEST, 1201, "잘못된 카카오 토큰 정보입니다."),
    FAILED_TO_RETRIEVE_KAKAO_USER_INFO(HttpStatus.BAD_REQUEST, 1202, "카카오 계정 정보를 가져오는 데 실패했습니다.");

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
