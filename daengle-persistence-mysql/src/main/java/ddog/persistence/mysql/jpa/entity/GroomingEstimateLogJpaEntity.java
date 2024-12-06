package ddog.persistence.jpa.entity;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.GroomingEstimateLog;
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
@Entity(name = "GroomingEstimateLogs")
public class GroomingEstimateLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateLogId;
    private Long estimateId;
    private Long groomerId;
    private Long userId;
    private Long petId;

    private String address;
    private LocalDateTime reservedDate;
    private String requirements;

    @Enumerated(EnumType.STRING)
    private EstimateStatus status;

    @Enumerated(EnumType.STRING)
    private Proposal proposal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String desiredStyle;
    private String overallOpinion;

    public static GroomingEstimateLogJpaEntity from(GroomingEstimateLog estimateLog) {
        return GroomingEstimateLogJpaEntity.builder()
                .estimateLogId(estimateLog.getEstimateLogId())
                .estimateId(estimateLog.getEstimateId())
                .groomerId(estimateLog.getGroomerId())
                .userId(estimateLog.getUserId())
                .petId(estimateLog.getPetId())
                .address(estimateLog.getAddress())
                .reservedDate(estimateLog.getReservedDate())
                .requirements(estimateLog.getRequirements())
                .status(estimateLog.getStatus())
                .proposal(estimateLog.getProposal())
                .createdAt(estimateLog.getCreatedAt())
                .updatedAt(estimateLog.getUpdatedAt())
                .desiredStyle(estimateLog.getDesiredStyle())
                .overallOpinion(estimateLog.getOverallOpinion())
                .build();
    }

    public GroomingEstimateLog toModel() {
        return GroomingEstimateLog.builder()
                .estimateLogId(estimateLogId)
                .estimateId(estimateId)
                .groomerId(groomerId)
                .userId(userId)
                .petId(petId)
                .address(address)
                .reservedDate(reservedDate)
                .requirements(requirements)
                .status(status)
                .proposal(proposal)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .desiredStyle(desiredStyle)
                .overallOpinion(overallOpinion)
                .build();
    }
}
