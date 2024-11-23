package ddog.daengleserver.infrastructure.jpa;

import ddog.daengleserver.domain.estimate.EstimateStatus;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.domain.estimate.Proposal;
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
@Entity(name = "GroomingEstimate")
public class GroomingEstimateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groomingEstimateId;
    private Long userId;
    private Long petId;
    private String userImage;
    private String nickname;
    private String petSignificant;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String address;

    @Enumerated(EnumType.STRING)
    private Proposal proposal;
    private Long groomerId;

    @Enumerated(EnumType.STRING)
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private String overallOpinion;

    public GroomingEstimate toModel() {
        return GroomingEstimate.builder()
                .groomingEstimateId(this.groomingEstimateId)
                .userId(this.userId)
                .petId(this.petId)
                .userImage(this.userImage)
                .nickname(this.nickname)
                .petSignificant(this.petSignificant)
                .reservedDate(this.reservedDate)
                .desiredStyle(this.desiredStyle)
                .requirements(this.requirements)
                .address(this.address)
                .proposal(this.proposal)
                .groomerId(this.groomerId)
                .status(this.status)
                .createdAt(this.createdAt)
                .overallOpinion(this.overallOpinion)
                .build();
    }

    public static GroomingEstimateJpaEntity from(GroomingEstimate groomingEstimate) {
        return GroomingEstimateJpaEntity.builder()
                .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                .userId(groomingEstimate.getUserId())
                .petId(groomingEstimate.getPetId())
                .userImage(groomingEstimate.getUserImage())
                .nickname(groomingEstimate.getNickname())
                .petSignificant(groomingEstimate.getPetSignificant())
                .reservedDate(groomingEstimate.getReservedDate())
                .desiredStyle(groomingEstimate.getDesiredStyle())
                .requirements(groomingEstimate.getRequirements())
                .address(groomingEstimate.getAddress())
                .proposal(groomingEstimate.getProposal())
                .groomerId(groomingEstimate.getGroomerId())
                .status(groomingEstimate.getStatus())
                .createdAt(groomingEstimate.getCreatedAt())
                .overallOpinion(groomingEstimate.getOverallOpinion())
                .build();
    }
}
