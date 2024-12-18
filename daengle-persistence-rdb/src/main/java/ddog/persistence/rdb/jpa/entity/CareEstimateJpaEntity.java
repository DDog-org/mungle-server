package ddog.persistence.rdb.jpa.entity;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CareEstimates")
public class CareEstimateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateId;
    private Long parentId;
    private Long userId;
    private Long petId;
    private Long vetId;

    private String address;
    private LocalDateTime reservedDate;
    private String requirements;

    @Enumerated(EnumType.STRING)
    private EstimateStatus status;

    @Enumerated(EnumType.STRING)
    private Proposal proposal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String symptoms;
    private String diagnosis;
    private String cause;
    private String treatment;

    public static CareEstimateJpaEntity from(CareEstimate estimate) {
        return CareEstimateJpaEntity.builder()
                .estimateId(estimate.getEstimateId())
                .parentId(estimate.getParentId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .vetId(estimate.getVetId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .requirements(estimate.getRequirements())
                .status(estimate.getStatus())
                .proposal(estimate.getProposal())
                .createdAt(estimate.getCreatedAt())
                .updatedAt(estimate.getUpdatedAt())
                .symptoms(estimate.getSymptoms())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }

    public CareEstimate toModel() {
        return CareEstimate.builder()
                .estimateId(estimateId)
                .parentId(parentId)
                .userId(userId)
                .petId(petId)
                .vetId(vetId)
                .address(address)
                .reservedDate(reservedDate)
                .requirements(requirements)
                .status(status)
                .proposal(proposal)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .symptoms(symptoms)
                .diagnosis(diagnosis)
                .cause(cause)
                .treatment(treatment)
                .build();
    }
}
