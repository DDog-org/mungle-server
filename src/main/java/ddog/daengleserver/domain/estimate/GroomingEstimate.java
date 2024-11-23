package ddog.daengleserver.domain.estimate;

import ddog.daengleserver.presentation.dto.request.DesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.GeneralGroomingEstimateReq;
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
    private Long userId;
    private Long petId;
    private String userImage;
    private String nickname;
    private String petSignificant;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String address;
    private Proposal proposal;
    private Long groomerId;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private String overallOpinion;

    public static GroomingEstimate createGeneralGroomingEstimate(GeneralGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .groomingEstimateId(null)
                .userId(request.getUserId())
                .petId(request.getPetId())
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .petSignificant(request.getPetSignificant())
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
                .requirements(request.getRequirements())
                .address(request.getAddress())
                .proposal(Proposal.GENERAL)
                .groomerId(null)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .overallOpinion(null)
                .build();
    }

    public static GroomingEstimate createDesignationGroomingEstimate(DesignationGroomingEstimateReq request) {
        return GroomingEstimate.builder()
                .groomingEstimateId(null)
                .userId(request.getUserId())
                .petId(request.getPetId())
                .userImage(request.getUserImage())
                .nickname(request.getNickname())
                .petSignificant(request.getPetSignificant())
                .reservedDate(request.getReservedDate())
                .desiredStyle(request.getDesiredStyle())
                .requirements(request.getRequirements())
                .address(request.getAddress())
                .proposal(Proposal.DESIGNATION)
                .groomerId(request.getGroomerId())
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .overallOpinion(null)
                .build();
    }

    public static List<GroomingEstimateInfo> withGroomingEstimate(List<GroomingEstimate> groomingEstimates) {

        List<GroomingEstimateInfo> groomingEstimateInfos = new ArrayList<>();

        for (GroomingEstimate groomingEstimate : groomingEstimates) {
            groomingEstimateInfos.add(GroomingEstimateInfo.builder()
                    .groomingEstimateId(groomingEstimate.getGroomingEstimateId())
                    .nickname(groomingEstimate.getNickname())
                    .userImage(groomingEstimate.getUserImage())
                    .petSignificant(groomingEstimate.getPetSignificant())
                    .address(groomingEstimate.getAddress())
                    .reservedDate(groomingEstimate.getReservedDate())
                    .build());
        }

        return groomingEstimateInfos;
    }
}
