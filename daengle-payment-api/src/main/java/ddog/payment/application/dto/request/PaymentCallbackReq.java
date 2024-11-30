package ddog.payment.application.dto.request;

import lombok.Getter;

@Getter
public class PaymentCallbackReq {
    private String paymentUid;
    private String orderUid;
}
