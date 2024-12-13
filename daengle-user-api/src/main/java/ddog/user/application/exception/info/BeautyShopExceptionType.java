package ddog.user.application.exception.info;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum BeautyShopExceptionType {
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "미용샵이 존재하지 않음.");

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
