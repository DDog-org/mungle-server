package ddog.domain.estimate.dto.response;

import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroomingEstimateDetail {

    private String image;
    private String name;
    private String shopName;
    private int daengleMeter;
    private String introduction;
    private String address;
    private LocalDateTime reservedDate;
    private Weight weight;
    private String desiredStyle;
    private String overallOpinion;

}
