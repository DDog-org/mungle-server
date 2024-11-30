package ddog.user.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.user.presentation.estimate.dto.DesignationGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.EstimateInfo;
import ddog.user.presentation.estimate.dto.GeneralGroomingEstimateReq;
import ddog.user.presentation.estimate.dto.GroomingEstimateDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroomingEstimateMapper {

    public static GroomingEstimate createGeneralGroomingEstimate(GeneralGroomingEstimateReq request, Long accountId) {
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

    public static GroomingEstimate createDesignationGroomingEstimate(DesignationGroomingEstimateReq request, Long accountId) {
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

    public static List<EstimateInfo.PetInfo.Grooming> toInfos(List<GroomingEstimate> groomingEstimates) {
        List<EstimateInfo.PetInfo.Grooming> groomingInfos = new ArrayList<>();
        for (GroomingEstimate groomingEstimate : groomingEstimates) {
            groomingInfos.add(EstimateInfo.PetInfo.Grooming.builder()
                    .id(groomingEstimate.getGroomingEstimateId())
                    .name(groomingEstimate.getGroomerName())
                    .daengleMeter(groomingEstimate.getDaengleMeter())
                    .image(groomingEstimate.getGroomerImage())
                    .shopName(groomingEstimate.getShopName())
                    .reservedDate(groomingEstimate.getReservedDate())
                    .build());
        }
        return groomingInfos;
    }

    public static GroomingEstimateDetail getGroomingEstimateDetail(GroomingEstimate groomingEstimate) {
        return GroomingEstimateDetail.builder()
                .image(groomingEstimate.getGroomerImage())
                .name(groomingEstimate.getGroomerName())
                .shopName(groomingEstimate.getShopName())
                .daengleMeter(groomingEstimate.getDaengleMeter())
                .introduction(groomingEstimate.getGroomerIntroduction())
                .address(groomingEstimate.getAddress())
                .reservedDate(groomingEstimate.getReservedDate())
                .weight(groomingEstimate.getPetWeight())
                .desiredStyle(groomingEstimate.getDesiredStyle())
                .overallOpinion(groomingEstimate.getOverallOpinion())
                .build();
    }

}
