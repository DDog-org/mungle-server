package ddog.payment.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentCancelResp {
    private Long paymentId;
    private Long reservationId;
    private Long originDepositAmount;
    private BigDecimal refundAmount;
}
