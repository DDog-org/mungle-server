package ddog.payment.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaymentHistoryListResp {
    private List<PaymentHistorySummaryResp> paymentHistoryList;
}
