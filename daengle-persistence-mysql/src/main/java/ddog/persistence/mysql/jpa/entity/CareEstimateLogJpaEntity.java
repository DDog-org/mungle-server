package ddog.persistence.mysql.jpa.entity;

import ddog.domain.estimate.CareEstimateLog;
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
@Entity(name = "CareEstimateLogs")
public class CareEstimateLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateLogId;
    private Long estimateId;
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

    public static CareEstimateLogJpaEntity from(CareEstimateLog estimateLog) {
        return CareEstimateLogJpaEntity.builder()
                .estimateLogId(estimateLog.getEstimateLogId())
                .estimateId(estimateLog.getEstimateId())
                .userId(estimateLog.getUserId())
                .petId(estimateLog.getPetId())
                .vetId(estimateLog.getVetId())
                .address(estimateLog.getAddress())
                .reservedDate(estimateLog.getReservedDate())
                .requirements(estimateLog.getRequirements())
                .status(estimateLog.getStatus())
                .proposal(estimateLog.getProposal())
                .createdAt(estimateLog.getCreatedAt())
                .updatedAt(estimateLog.getUpdatedAt())
                .symptoms(estimateLog.getSymptoms())
                .diagnosis(estimateLog.getDiagnosis())
                .cause(estimateLog.getCause())
                .treatment(estimateLog.getTreatment())
                .build();
    }

    public CareEstimateLog toModel() {
        return CareEstimateLog.builder()
                .estimateLogId(estimateLogId)
                .estimateId(estimateId)
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
