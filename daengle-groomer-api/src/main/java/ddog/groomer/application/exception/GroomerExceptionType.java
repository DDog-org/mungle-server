package ddog.groomer.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GroomerExceptionType {
    INVALID_REQUEST_DATA_FORMAT(HttpStatus.BAD_REQUEST, 400, "데이터 형식 오류"),
    GROOMER_NOT_FOUND(HttpStatus.BAD_REQUEST, 404, "존재하지 않는 미용사");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
