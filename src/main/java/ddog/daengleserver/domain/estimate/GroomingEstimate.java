package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.domain.Weight;
import ddog.daengleserver.presentation.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.UserGroomingEstimateDetails;
import ddog.daengleserver.presentation.dto.response.UserGroomingEstimateInfo;
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
    private String overallOpinion;
    private String groomerImage;
    private String groomerName;
    private String shopName;
    private String groomerIntroduction;


    public static GroomingEstimate createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
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

    public static GroomingEstimate createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
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
                .groomerId(request.getGroomerId())
                .build();
    }

    public static List<UserGroomingEstimateInfo> withUserGroomingEstimates(List<GroomingEstimate> groomingEstimates) {

        List<UserGroomingEstimateInfo> userGroomingEstimateInfos = new ArrayList<>();

        for (GroomingEstimate groomingEstimate : groomingEstimates) {
            userGroomingEstimateInfos.add(UserGroomingEstimateInfo.builder()
                    .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                    .reservedDate(groomingEstimate.getReservedDate())
                    .address(groomingEstimate.getAddress())
                    .userImage(groomingEstimate.getUserImage())
                    .nickname(groomingEstimate.getNickname())
                    .petSignificant(groomingEstimate.getPetSignificant())
                    .build());
        }

        return userGroomingEstimateInfos;
    }

    public UserGroomingEstimateDetails withUserGroomingEstimate() {
        return UserGroomingEstimateDetails.builder()
                .groomingEstimateId(groomingEstimateId)
                .reservedDate(reservedDate)
                .nickname(nickname)
                .userImage(userImage)
                .address(address)
                .petImage(petImage)
                .petBirth(petBirth)
                .petSignificant(petSignificant)
                .petName(petName)
                .petSignificant(petSignificant)
                .petBirth(petBirth)
                .petWeight(petWeight)
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
                .overallOpinion(request.getOverallOpinion())
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .build();
    }
}
