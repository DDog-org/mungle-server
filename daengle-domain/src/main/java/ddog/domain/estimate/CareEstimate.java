package ddog.domain.estimate;

import ddog.domain.estimate.dto.request.VetCareEstimateReq;
import ddog.domain.estimate.dto.response.CareEstimateInfo;
import ddog.domain.estimate.dto.response.UserCareEstimateDetail;
import ddog.domain.pet.Weight;
import ddog.domain.vet.Vet;
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

}
