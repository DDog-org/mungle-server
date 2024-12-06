package ddog.payment.application.dto.request;

import lombok.Getter;

@Getter
public class PaymentCallbackReq {
    private String paymentUid;
    private Long estimateId;
    private String orderUid;
}
