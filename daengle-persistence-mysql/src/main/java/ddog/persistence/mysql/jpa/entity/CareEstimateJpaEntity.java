package ddog.persistence.mysql.jpa.entity;

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
    private Long careEstimateId;
    private Long userId;
    private Long petId;
    private Long vetId;
    private String address;
    private LocalDateTime reservedDate;
    private String symptoms;
    private String requirements;

    @Enumerated(EnumType.STRING)
    private Proposal proposal;

    @Enumerated(EnumType.STRING)
    private EstimateStatus status;

    private LocalDateTime createdAt;
    private String diagnosis;
    private String cause;
    private String treatment;


    public static CareEstimateJpaEntity from(CareEstimate estimate) {
        return CareEstimateJpaEntity.builder()
                .careEstimateId(estimate.getCareEstimateId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .vetId(estimate.getVetId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .proposal(estimate.getProposal())
                .status(estimate.getStatus())
                .createdAt(estimate.getCreatedAt())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }

    public CareEstimate toModel() {
        return CareEstimate.builder()
                .careEstimateId(careEstimateId)
                .userId(userId)
                .petId(petId)
                .vetId(vetId)
                .address(address)
                .reservedDate(reservedDate)
                .symptoms(symptoms)
                .requirements(requirements)
                .proposal(proposal)
                .status(status)
                .createdAt(createdAt)
                .diagnosis(diagnosis)
                .cause(cause)
                .treatment(treatment)
                .build();
    }
}
