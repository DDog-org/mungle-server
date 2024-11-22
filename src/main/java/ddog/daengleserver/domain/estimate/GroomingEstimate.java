package ddog.daengleserver.domain.estimate;

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

    private Long userId;
    private Long petId;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String address;
    private Proposal proposal;
    private Long designatedGroomerId;
    private Long groomerId;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private String overallOpinion;

}
