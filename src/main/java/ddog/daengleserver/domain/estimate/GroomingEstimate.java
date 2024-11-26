package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.domain.Weight;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.EstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfos;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetails;
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


    public static GroomingEstimate createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request, Long userId) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
                .requirements(request.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(userId)
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

    public static GroomingEstimate createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request, Long userId) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
                .requirements(request.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .address(request.getAddress())
                .petId(request.getPetId())
                .petImage(request.getPetImage())
                .petName(request.getPetName())
                .petBirth(request.getPetBirth())
                .petWeight(request.getPetWeight())
                .petSignificant(request.getPetSignificant())
                .groomerId(request.getGroomerId())
                .build();
    }

    public static GroomingEstimateInfos findGroomingEstimateInfos(
            List<GroomingEstimate> generalEstimates,
            List<GroomingEstimate> designationEstimates
    ) {
        List<GroomingEstimateInfos.Contents> allContents = estimatesToContents(generalEstimates);
        List<GroomingEstimateInfos.Contents> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // groomingEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(GroomingEstimateInfos.Contents::getGroomingEstimateId));

        return GroomingEstimateInfos.builder()
                .allGroomingEstimates(allContents)
                .designationGroomingEstimates(designationContents)
                .build();
    }

    private static List<GroomingEstimateInfos.Contents> estimatesToContents(List<GroomingEstimate> estimates) {
        List<GroomingEstimateInfos.Contents> contents = new ArrayList<>();
        for (GroomingEstimate estimate : estimates) {
            contents.add(GroomingEstimateInfos.Contents.builder()
                    .groomingEstimateId(estimate.getGroomingEstimateId())
                    .userImage(estimate.getUserImage())
                    .nickname(estimate.getNickname())
                    .proposal(estimate.getProposal())
                    .petSignificant(estimate.getPetSignificant())
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

    public UserGroomingEstimateDetails withUserGroomingEstimate() {
        return UserGroomingEstimateDetails.builder()
                .groomingEstimateId(groomingEstimateId)
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .reservedDate(reservedDate)
                .petId(petId)
                .petImage(petImage)
                .petName(petName)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .petSignificant(petSignificant)
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
