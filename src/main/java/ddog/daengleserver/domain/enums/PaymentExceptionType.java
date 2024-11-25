package ddog.daengleserver.domain.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PaymentExceptionType {
    PAYMENT_PG_INTEGRATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "결제 정보 조회 중 에러 발생"),
    PAYMENT_PG_INCOMPLETE(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "미완료된 결제건"),
    PAYMENT_PG_AMOUNT_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "미완료된 결제건");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
