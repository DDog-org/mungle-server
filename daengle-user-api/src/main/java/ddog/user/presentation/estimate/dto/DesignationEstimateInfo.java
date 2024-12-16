package ddog.user.presentation.estimate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignationEstimateInfo {
    private Long partnerId;
    private String partnerPhone;
    private String partnerName;
}
