package ddog.payment.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentCallbackResp {
    private Long customerId;
    private Long reservationId;
    private Long paymentId;
    private Long price;
}
