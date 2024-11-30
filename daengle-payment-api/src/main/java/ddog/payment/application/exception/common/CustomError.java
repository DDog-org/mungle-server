package ddog.payment.application.exception.common;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomError {
    private final String message;
    private final int status;
    private final Integer code;

    public CustomError(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(), status, null);
    }

    public CustomError(String message, HttpStatus status) {
        this(message, status, null);
    }

    public CustomError(String message, HttpStatus status, Integer code) {
        this.message = message;
        this.status = status.value();
        this.code = code;
    }
}
