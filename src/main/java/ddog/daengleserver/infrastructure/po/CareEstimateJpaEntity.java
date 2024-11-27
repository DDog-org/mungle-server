package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.account.enums.Weight;
import ddog.daengleserver.domain.estimate.CareEstimate;
import ddog.daengleserver.domain.estimate.EstimateStatus;
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
@Entity(name = "CareEstimates")
public class CareEstimateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careEstimateId;
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

    private Long userId;
    private String userImage;
    private String nickname;
    private String address;

    private Long petId;
    private String petImage;
    private String petName;
    private int petBirth;
    @Enumerated(EnumType.STRING)
    private Weight petWeight;
    private String petSignificant;

    private Long vetId;
    private int daengleMeter;
    private String vetImage;
    private String vetName;
    private String vetIntroduction;

    public static CareEstimateJpaEntity from(CareEstimate careEstimate) {
        return CareEstimateJpaEntity.builder()
                .careEstimateId(careEstimate.getCareEstimateId())
                .reservedDate(careEstimate.getReservedDate())
                .symptoms(careEstimate.getSymptoms())
                .requirements(careEstimate.getRequirements())
                .proposal(careEstimate.getProposal())
                .status(careEstimate.getStatus())
                .createdAt(careEstimate.getCreatedAt())
                .diagnosis(careEstimate.getDiagnosis())
                .cause(careEstimate.getCause())
                .treatment(careEstimate.getTreatment())
                .userId(careEstimate.getUserId())
                .userImage(careEstimate.getUserImage())
                .nickname(careEstimate.getNickname())
                .address(careEstimate.getAddress())
                .petId(careEstimate.getPetId())
                .petImage(careEstimate.getPetImage())
                .petName(careEstimate.getPetName())
                .petBirth(careEstimate.getPetBirth())
                .petWeight(careEstimate.getPetWeight())
                .petSignificant(careEstimate.getPetSignificant())
                .vetId(careEstimate.getVetId())
                .daengleMeter(careEstimate.getDaengleMeter())
                .vetImage(careEstimate.getVetImage())
                .vetName(careEstimate.getVetName())
                .vetIntroduction(careEstimate.getVetIntroduction())
                .build();
    }

    public CareEstimate toModel() {
        return CareEstimate.builder()
                .careEstimateId(careEstimateId)
                .reservedDate(reservedDate)
                .symptoms(symptoms)
                .requirements(requirements)
                .proposal(proposal)
                .status(status)
                .createdAt(createdAt)
                .diagnosis(diagnosis)
                .cause(cause)
                .treatment(treatment)
                .userId(userId)
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .petId(petId)
                .petImage(petImage)
                .petName(petName)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .petSignificant(petSignificant)
                .vetId(vetId)
                .daengleMeter(daengleMeter)
                .vetImage(vetImage)
                .vetName(vetName)
                .vetIntroduction(vetIntroduction)
                .build();
    }
}
