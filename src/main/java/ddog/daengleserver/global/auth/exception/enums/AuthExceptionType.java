package ddog.daengleserver.global.auth.exception.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthExceptionType {
    UNAVAILABLE_TOKEN(HttpStatus.BAD_REQUEST, "400"),
    UNAVAILABLE_ROLE(HttpStatus.BAD_REQUEST, "400"),
    RESPONSE_CODE_ERROR(HttpStatus.BAD_REQUEST, "400"),
    FAILED_TO_RETRIEVE_KAKAO_USER_INFO(HttpStatus.BAD_REQUEST, "400");

    private final HttpStatus status;
    private final String code;
}
