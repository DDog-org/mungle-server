package ddog.domain.payment.enums;

public enum PaymentStatus {
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_CANCELED("결제 취소"),
    PAYMENT_INVALIDATION("유효하지 않은 결제"),
    SERVICE_COMPLETED("서비스완료");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}