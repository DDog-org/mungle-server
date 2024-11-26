package ddog.daengleserver.presentation.estimate.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CareEstimateDetails {

    private Long careEstimateId;
    private String vetImage;
    private String vetName;
    private int daengleMeter;
    private String vetIntroduction;
    private String address;
    private LocalDateTime reservedDate;
    private String diagnosis;
    private String cause;
    private String treatment;

}
