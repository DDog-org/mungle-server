package ddog.vet.exception.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private ResponseEntity<CommonResponseEntity<Object>> response(CustomRuntimeException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(
                new CommonResponseEntity<>(
                        false,
                        null,
                        new CustomError(exception.getMessage(), exception.getHttpStatus(), exception.getCode())
                ),
                headers,
                exception.getHttpStatus()
        );
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<?> handleCustomRuntimeException(CustomRuntimeException e) {
        return response(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e) {
        return new ResponseEntity<>(
                new CommonResponseEntity<>(
                        false,
                        null,
                        new CustomError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null)
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
