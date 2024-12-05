package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingEstimateLog extends GroomingEstimate {

    private Long estimateLogId;

    public static GroomingEstimateLog from(GroomingEstimate estimate) {
        return GroomingEstimateLog.builder()
                .estimateId(estimate.getEstimateId())
                .groomerId(estimate.getGroomerId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .requirements(estimate.getRequirements())
                .status(estimate.getStatus())
                .proposal(estimate.getProposal())
                .createdAt(estimate.getUpdatedAt())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }
}
