package ddog.domain.payment;

import ddog.domain.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private Long payerId;
    private Long price;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String paymentUid;

    public static final String PAYMENT_SUCCESS_STATUS = "paid";

    //결제 완료 확인
    public boolean checkIncompleteBy(String paymentStatus) {
        return !Objects.equals(paymentStatus, PAYMENT_SUCCESS_STATUS);
    }

    //결제 금액 유효성 검증
    public boolean checkInValidationBy(Long paymentAmount) {
        return !Objects.equals(this.price, paymentAmount);
    }

    public void validationSuccess(String impUid) {
        this.paymentUid = impUid;
        this.status = PaymentStatus.COMPLETED;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCEL;
    }
}
