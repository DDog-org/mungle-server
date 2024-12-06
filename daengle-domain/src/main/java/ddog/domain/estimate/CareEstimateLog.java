package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CareEstimateLog extends CareEstimate {

    private Long estimateLogId;

    public static CareEstimateLog from(CareEstimate estimate) {
        return CareEstimateLog.builder()
                .estimateId(estimate.getEstimateId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .vetId(estimate.getVetId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .requirements(estimate.getRequirements())
                .status(estimate.getStatus())
                .proposal(estimate.getProposal())
                .createdAt(estimate.getUpdatedAt())
                .symptoms(estimate.getSymptoms())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }
}

