package ddog.payment.application.exception.common;


import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public record CommonResponseEntity<T>(boolean success, T response, CustomError error) {

    public static <T> CommonResponseEntity<T> success(T response) {
        return new CommonResponseEntity<>(true, response, null);
    }

    public static <T> CommonResponseEntity<T> error(T response, HttpStatus status, String message) {
        return new CommonResponseEntity<>(false, null, new CustomError(message, status));
    }

    public Map<String, Object> to() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("response", response);
        result.put("error", error);
        return result;
    }
}
