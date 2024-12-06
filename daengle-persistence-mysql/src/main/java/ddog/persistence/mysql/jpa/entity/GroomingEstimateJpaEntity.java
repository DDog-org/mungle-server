package ddog.persistence.mysql.jpa.entity;

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
    private Long groomingEstimateId;
    private Long userId;
    private Long petId;
    private Long groomerId;

    private String address;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String overallOpinion;

    @Enumerated(EnumType.STRING)
    private Proposal proposal;

    @Enumerated(EnumType.STRING)
    private EstimateStatus status;

    private LocalDateTime createdAt;

    public static GroomingEstimateJpaEntity from(GroomingEstimate estimate) {
        return GroomingEstimateJpaEntity.builder()
                .groomingEstimateId(estimate.getGroomingEstimateId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .groomerId(estimate.getGroomerId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .overallOpinion(estimate.getOverallOpinion())
                .proposal(estimate.getProposal())
                .status(estimate.getStatus())
                .createdAt(estimate.getCreatedAt())
                .build();
    }

    public GroomingEstimate toModel() {
        return GroomingEstimate.builder()
                .groomingEstimateId(groomingEstimateId)
                .userId(userId)
                .petId(petId)
                .groomerId(groomerId)
                .address(address)
                .reservedDate(reservedDate)
                .desiredStyle(desiredStyle)
                .requirements(requirements)
                .overallOpinion(overallOpinion)
                .proposal(proposal)
                .status(status)
                .createdAt(createdAt)
                .build();
    }
}
