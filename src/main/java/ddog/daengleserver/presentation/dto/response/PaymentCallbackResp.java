package ddog.daengleserver.presentation.dto.response;

import lombok.Builder;

@Builder
public class PaymentCallbackResp {
    private Long userId;
    private Long paymentId;
    private Long price;
}
