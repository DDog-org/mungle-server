package ddog.mungleserver.global.auth.exception.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthExceptionType {
    UNAVAILABLE_TOKEN(HttpStatus.BAD_REQUEST, "400");

    private final HttpStatus status;
    private final String code;
}
