package ddog.daengleserver.domain.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OrderExceptionType {
    ORDER_NOT_FOUNDED(HttpStatus.NOT_FOUND, 6001, "주문 내역 존재하지 않음");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
