package ddog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountExceptionType {
    ACCOUNT_EXCEPTION_TYPE(HttpStatus.NOT_FOUND, 404, "유저를 찾을 수 없음"),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, 404, "유저를 찾을 수 없음"),;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
