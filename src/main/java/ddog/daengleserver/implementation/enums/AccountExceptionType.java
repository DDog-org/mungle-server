package ddog.daengleserver.implementation.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AccountExceptionType {
    ACCOUNT_EXCEPTION_TYPE(HttpStatus.NOT_FOUND, "404"),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "404"),;

    private HttpStatus status;
    private String code;

}
