package ddog.user.presentation.estimate.dto;

import ddog.domain.estimate.Proposal;
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
    private Proposal proposal;
    private String introduction;
    private String address;
    private LocalDateTime reservedDate;
    private Weight weight;
    private String desiredStyle;
    private String overallOpinion;

}
