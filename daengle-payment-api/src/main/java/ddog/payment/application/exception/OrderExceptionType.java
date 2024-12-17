package ddog.payment.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OrderExceptionType {
    ORDER_USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, 404, "존재하지 않는 유저"),
    ORDER_NOT_FOUNDED(HttpStatus.NOT_FOUND, 6001, "주문 내역 존재하지 않음"),
    ORDER_ALREADY_PROCESSED(HttpStatus.CONFLICT, 409, "중복된 주문 요청");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    public org.springframework.http.HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
