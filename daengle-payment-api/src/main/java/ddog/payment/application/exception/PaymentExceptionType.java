package ddog.payment.application.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PaymentExceptionType {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "결제 정보 찾을 수 없음"),
    PAYMENT_USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "결제자 찾을 수 없음"),
    PAYMENT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "결제 내역 찾을 수 없음"),
    PAYMENT_RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "예약 내역 찾을 수 없음"),
    PAYMENT_ALREADY_PROCESSED(HttpStatus.CONFLICT, 409, "중복된 결제 검증 요청"),
    PAYMENT_PG_INTEGRATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "결제 정보 조회 중 에러 발생"),
    PAYMENT_PG_INCOMPLETE(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "미완료된 결제건"),
    PAYMENT_PG_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, 5003, "PG사 API 연결 타임 아웃"),
    PAYMENT_ALREADY_COMPLETED(HttpStatus.NOT_FOUND, 5004, "이미 결제된 결제건"),
    PAYMENT_PG_AMOUNT_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, 5005, "결제 금액 불일치"),
    PAYMENT_CANCEL_BATCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5006, "예약 취소 배치 처리 중 에러 발생");


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
