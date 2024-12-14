package ddog.user.presentation.estimate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.estimate.Proposal;
import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroomingEstimateDetail {

    private Long groomingEstimateId;
    private Long groomerId;
    private String imageUrl;
    private String name;
    private Long shopId;
    private String shopName;
    private int daengleMeter;
    private Proposal proposal;
    private String introduction;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedDate;
    private Weight weight;
    private String desiredStyle;
    private String overallOpinion;

}
