package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.domain.account.Groomer;
import ddog.daengleserver.domain.account.enums.Weight;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.EstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetail;
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
public class GroomingEstimate {

    private Long groomingEstimateId;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;

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

    private Long groomerId;
    private int daengleMeter;
    private String overallOpinion;
    private String groomerImage;
    private String groomerName;
    private String shopName;
    private String groomerIntroduction;


    public static GroomingEstimate createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request, Long accountId) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
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

    public static GroomingEstimate createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request, Long accountId) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
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
                .groomerId(request.getGroomerId())
                .build();
    }

    public static GroomingEstimateInfo toGroomingEstimateInfo(
            List<GroomingEstimate> generalEstimates,
            List<GroomingEstimate> designationEstimates
    ) {
        List<GroomingEstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<GroomingEstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // groomingEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(GroomingEstimateInfo.Content::getId));

        return GroomingEstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private static List<GroomingEstimateInfo.Content> estimatesToContents(List<GroomingEstimate> estimates) {
        List<GroomingEstimateInfo.Content> contents = new ArrayList<>();
        for (GroomingEstimate estimate : estimates) {
            contents.add(GroomingEstimateInfo.Content.builder()
                    .id(estimate.getGroomingEstimateId())
                    .image(estimate.getUserImage())
                    .nickname(estimate.getNickname())
                    .proposal(estimate.getProposal())
                    .significant(estimate.getPetSignificant())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return contents;
    }

    public static List<EstimateInfo.PetInfo.Grooming> toInfos(List<GroomingEstimate> groomingEstimates) {
        List<EstimateInfo.PetInfo.Grooming> groomingInfos = new ArrayList<>();
        for (GroomingEstimate groomingEstimate : groomingEstimates) {
            groomingInfos.add(EstimateInfo.PetInfo.Grooming.builder()
                    .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                    .groomerName(groomingEstimate.getGroomerName())
                    .daengleMeter(groomingEstimate.getDaengleMeter())
                    .groomerImage(groomingEstimate.getGroomerImage())
                    .shopName(groomingEstimate.getShopName())
                    .reservedDate(groomingEstimate.getReservedDate())
                    .build());
        }
        return groomingInfos;
    }

    public UserGroomingEstimateDetail toUserGroomingEstimateDetail() {
        return UserGroomingEstimateDetail.builder()
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .reservedDate(reservedDate)
                .id(petId)
                .petImage(petImage)
                .name(petName)
                .birth(petBirth)
                .weight(petWeight)
                .significant(petSignificant)
                .desiredStyle(desiredStyle)
                .requirements(requirements)
                .build();
    }

    public GroomingEstimate createGroomerGroomingEstimate(GroomerGroomingEstimateReq request, Groomer groomer) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(desiredStyle)
                .requirements(requirements)
                .proposal(proposal)
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
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
                .groomerId(groomer.getGroomerId())
                .daengleMeter(groomer.getDaengleMeter())
                .overallOpinion(request.getOverallOpinion())
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .build();
    }

    public GroomingEstimateDetails getGroomingEstimateDetails() {
        return GroomingEstimateDetails.builder()
                .groomingEstimateId(groomingEstimateId)
                .groomerImage(groomerImage)
                .groomerName(groomerName)
                .shopName(shopName)
                .daengleMeter(daengleMeter)
                .groomerIntroduction(groomerIntroduction)
                .address(address)
                .reservedDate(reservedDate)
                .petWeight(petWeight)
                .desiredStyle(desiredStyle)
                .overallOpinion(overallOpinion)
                .build();
    }

}
