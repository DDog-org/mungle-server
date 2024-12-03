package ddog.user.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstimateResp {

    @Getter
    @Builder
    public static class Grooming {
        private String requestResult;
    }
}
