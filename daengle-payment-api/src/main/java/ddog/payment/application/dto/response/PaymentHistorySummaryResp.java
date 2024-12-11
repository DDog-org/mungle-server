package ddog.payment.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentHistorySummaryResp {
    private Long reservationId;
    private String recipientImageUrl;
    private String recipientName;
    private String shopName;
    private LocalDateTime paymentDate;
    private String status;
}
