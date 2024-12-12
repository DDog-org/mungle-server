package ddog.user.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstimateResp {
    private Long estimateId;
    private String requestResult;
}
