package ddog.payment.application.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CancelPaymentsRequest {
    private List<Long> reservationIds;
}
