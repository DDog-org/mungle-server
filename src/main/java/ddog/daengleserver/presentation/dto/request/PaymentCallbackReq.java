package ddog.daengleserver.presentation.dto.request;

import lombok.Getter;

@Getter
public class PaymentCallbackReq {
    private String paymentUid;
    private String orderUid;
}
