package ddog.estimate;


import ddog.estimate.dto.request.UserDesignationCareEstimateReq;
import ddog.estimate.dto.request.UserGeneralCareEstimateReq;
import ddog.estimate.dto.request.VetCareEstimateReq;
import ddog.estimate.dto.response.CareEstimateDetail;
import ddog.estimate.dto.response.CareEstimateInfo;
import ddog.estimate.dto.response.EstimateInfo;
import ddog.estimate.dto.response.UserCareEstimateDetail;
import ddog.pet.Weight;
import ddog.vet.Vet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    private int daengleMeter;
    private String vetImage;
    private String vetName;
    private String vetIntroduction;


    public static CareEstimate createUserGeneralCareEstimate(UserGeneralCareEstimateReq request, Long accountId) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(accountId)
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .petId(request.getId())
                .petImage(request.getPetImage())
                .petName(request.getName())
                .petBirth(request.getBirth())
                .petWeight(request.getWeight())
                .petSignificant(request.getSignificant())
                .build();
    }

    public static CareEstimate createUserDesignationCareEstimate(UserDesignationCareEstimateReq request, Long accountId) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(accountId)
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .petId(request.getPetId())
                .petImage(request.getPetImage())
                .petName(request.getName())
                .petBirth(request.getBirth())
                .petWeight(request.getWeight())
                .petSignificant(request.getSignificant())
                .vetId(request.getVetId())
                .build();
    }

    public static CareEstimateInfo findCareEstimateInfo(
            List<CareEstimate> generalEstimates,
            List<CareEstimate> designationEstimates
    ) {
        List<CareEstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<CareEstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // careEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(CareEstimateInfo.Content::getId));
        return CareEstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private static List<CareEstimateInfo.Content> estimatesToContents(List<CareEstimate> estimates) {
        List<CareEstimateInfo.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            contents.add(CareEstimateInfo.Content.builder()
                    .id(estimate.getCareEstimateId())
                    .image(estimate.getUserImage())
                    .nickname(estimate.getNickname())
                    .proposal(estimate.getProposal())
                    .significant(estimate.getPetSignificant())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return contents;
    }

    public static List<EstimateInfo.PetInfo.Care> toInfos(List<CareEstimate> careEstimates) {
        List<EstimateInfo.PetInfo.Care> careInfos = new ArrayList<>();
        for (CareEstimate careEstimate : careEstimates) {
            careInfos.add(EstimateInfo.PetInfo.Care.builder()
                    .id(careEstimate.getCareEstimateId())
                    .name(careEstimate.getVetName())
                    .daengleMeter(careEstimate.getDaengleMeter())
                    .image(careEstimate.getVetImage())
                    .reservedDate(careEstimate.getReservedDate())
                    .build());
        }
        return careInfos;
    }

    public UserCareEstimateDetail toUserCareEstimateDetail() {
        return UserCareEstimateDetail.builder()
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .reservedDate(reservedDate)
                .id(petId)
                .petImage(petImage)
                .birth(petBirth)
                .weight(petWeight)
                .significant(petSignificant)
                .name(petName)
                .symptoms(symptoms)
                .requirements(requirements)
                .build();
    }

    public CareEstimate createVetCareEstimate(VetCareEstimateReq request, Vet vet) {
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
                .daengleMeter(vet.getDaengleMeter())
                .vetImage(vet.getVetImage())
                .vetName(vet.getVetName())
                .vetIntroduction(vet.getVetIntroduction())
                .build();
    }

    public CareEstimateDetail getCareEstimateDetail() {
        return CareEstimateDetail.builder()
                .image(vetImage)
                .name(vetName)
                .daengleMeter(daengleMeter)
                .introduction(vetIntroduction)
                .address(address)
                .reservedDate(reservedDate)
                .diagnosis(diagnosis)
                .cause(cause)
                .treatment(treatment)
                .build();
    }
}
