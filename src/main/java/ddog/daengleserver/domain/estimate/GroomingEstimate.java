package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.domain.Weight;
import ddog.daengleserver.presentation.dto.request.DesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.GeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.GroomingEstimateDetails;
import ddog.daengleserver.presentation.dto.response.GroomingEstimateInfo;
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


    public static GroomingEstimate createGeneralGroomingEstimate(GeneralGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .groomingEstimateId(null)
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
                .groomerId(null)
                .overallOpinion(null)
                .build();
    }

    public static GroomingEstimate createDesignationGroomingEstimate(DesignationGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .groomingEstimateId(null)
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
                .overallOpinion(null)
                .build();
    }

    public static List<GroomingEstimateInfo> withGroomingEstimates(List<GroomingEstimate> groomingEstimates) {

        List<GroomingEstimateInfo> groomingEstimateInfos = new ArrayList<>();

        for (GroomingEstimate groomingEstimate : groomingEstimates) {
            groomingEstimateInfos.add(GroomingEstimateInfo.builder()
                    .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                    .reservedDate(groomingEstimate.getReservedDate())
                    .address(groomingEstimate.getAddress())
                    .userImage(groomingEstimate.getUserImage())
                    .nickname(groomingEstimate.getNickname())
                    .petSignificant(groomingEstimate.getPetSignificant())
                    .build());
        }

        return groomingEstimateInfos;
    }

    public GroomingEstimateDetails withGroomingEstimate() {
        return GroomingEstimateDetails.builder()
                .groomingEstimateId(this.groomingEstimateId)
                .reservedDate(this.reservedDate)
                .nickname(this.nickname)
                .userImage(this.userImage)
                .address(this.address)
                .petImage(this.petImage)
                .petBirth(this.petBirth)
                .petSignificant(this.petSignificant)
                .petName(this.petName)
                .petSignificant(this.petSignificant)
                .petBirth(this.petBirth)
                .petWeight(this.petWeight)
                .build();
    }
}
