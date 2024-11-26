package ddog.daengleserver.presentation.estimate.dto.response;

import ddog.daengleserver.domain.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroomingEstimateDetails {

    private Long groomingEstimateId;
    private String groomerImage;
    private String groomerName;
    private String shopName;
    private int daengleMeter;
    private String groomerIntroduction;
    private String address;
    private LocalDateTime reservedDate;
    private Weight petWeight;
    private String desiredStyle;
    private String overallOpinion;

}
