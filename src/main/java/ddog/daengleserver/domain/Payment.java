package ddog.daengleserver.domain;

import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.enums.PaymentStatus;
import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private Long price;
    private PaymentStatus status;
    private String paymentUid;

    private PaymentRepository paymentRepository;

    public static Payment createTemporaryHistoryBy(PostOrderReq postOrderReq) {
        return Payment.builder()
                .paymentId(null)
                .price(postOrderReq.getPrice())
                .status(PaymentStatus.READY)
                .paymentUid(null)
                .build();
    }

    public void updatePaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCEL;
    }

    public void validationSuccess(String impUid) {
        this.paymentUid = impUid;
        this.status = PaymentStatus.COMPLETED;
    }
}
