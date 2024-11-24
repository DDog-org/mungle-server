package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.domain.Vet;
import ddog.daengleserver.domain.Weight;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.VetCareEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.UserCareEstimateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareEstimate {

    private Long careEstimateId;
    private LocalDateTime reservedDate;
    private String symptoms;
    private String requirements;
    private Proposal proposal;
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
    private Weight petWeight;
    private String petSignificant;

    private Long vetId;
    private String vetImage;
    private String vetName;
    private String vetIntroduction;


    public static CareEstimate createUserGeneralCareEstimate(UserGeneralCareEstimateReq request) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(request.getUserId())
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .petId(request.getPetId())
                .petImage(request.getPetImage())
                .petName(request.getPetName())
                .petBirth(request.getPetBirth())
                .petWeight(request.getPetWeight())
                .petSignificant(request.getPetSignificant())
                .build();
    }

    public static CareEstimate createUserDesignationCareEstimate(UserDesignationCareEstimateReq request) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(request.getUserId())
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .petId(request.getPetId())
                .petImage(request.getPetImage())
                .petName(request.getPetName())
                .petBirth(request.getPetBirth())
                .petWeight(request.getPetWeight())
                .petSignificant(request.getPetSignificant())
                .vetId(request.getVetId())
                .build();
    }

    public static List<UserCareEstimateInfo> withUserCareEstimates(List<CareEstimate> careEstimates) {

        List<UserCareEstimateInfo> userCareEstimateInfos = new ArrayList<>();

        for (CareEstimate careEstimate : careEstimates) {
            userCareEstimateInfos.add(UserCareEstimateInfo.builder()
                    .careEstimateId(careEstimate.getCareEstimateId())
                    .userImage(careEstimate.getUserImage())
                    .nickname(careEstimate.getNickname())
                    .proposal(careEstimate.getProposal())
                    .petSignificant(careEstimate.getPetSignificant())
                    .reservedDate(careEstimate.getReservedDate())
                    .build());
        }

        return userCareEstimateInfos;
    }

    public UserCareEstimateDetails withUserCareEstimate() {
        return UserCareEstimateDetails.builder()
                .careEstimateId(careEstimateId)
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .reservedDate(reservedDate)
                .petImage(petImage)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .petSignificant(petSignificant)
                .petName(petName)
                .symptoms(symptoms)
                .requirements(requirements)
                .build();
    }

    public CareEstimate createVetGroomingEstimate(VetCareEstimateReq request, Vet vet) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(symptoms)
                .requirements(requirements)
                .proposal(proposal)
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .diagnosis(request.getDiagnosis())
                .cause(request.getCause())
                .treatment(request.getTreatment())
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
                .vetId(vet.getVetId())
                .vetImage(vet.getVetImage())
                .vetName(vet.getVetName())
                .vetIntroduction(vet.getVetIntroduction())
                .build();
    }
}
