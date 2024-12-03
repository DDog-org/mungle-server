package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingEstimate {

    private Long groomingEstimateId;
    private Long userId;
    private Long petId;
    private Long groomerId;

    private String address;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String overallOpinion;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;

}
