package ddog.persistence.rdb.jpa.entity;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
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
@Entity(name = "GroomingEstimates")
public class GroomingEstimateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateId;
    private Long parentId;
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

    public static GroomingEstimateJpaEntity from(GroomingEstimate estimate) {
        return GroomingEstimateJpaEntity.builder()
                .estimateId(estimate.getEstimateId())
                .parentId(estimate.getParentId())
                .groomerId(estimate.getGroomerId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .requirements(estimate.getRequirements())
                .status(estimate.getStatus())
                .proposal(estimate.getProposal())
                .createdAt(estimate.getCreatedAt())
                .updatedAt(estimate.getUpdatedAt())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }

    public GroomingEstimate toModel() {
        return GroomingEstimate.builder()
                .estimateId(estimateId)
                .parentId(parentId)
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
