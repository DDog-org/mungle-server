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

    public static GroomingEstimate createGeneralGroomingEstimate(GeneralGroomingEstimateReq estimateReq, Long accountId) {
        return GroomingEstimate.builder()
                .reservedDate(estimateReq.getReservedDate())
                .desiredStyle(estimateReq.getDesiredStyle())
                .requirements(estimateReq.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(accountId)
                .userImage(estimateReq.getUserImage())
                .nickname(estimateReq.getNickname())
                .address(estimateReq.getAddress())
                .petId(estimateReq.getId())
                .petImage(estimateReq.getPetImage())
                .petName(estimateReq.getName())
                .petBirth(estimateReq.getBirth())
                .petWeight(estimateReq.getWeight())
                .petSignificant(estimateReq.getSignificant())
                .build();
    }

    public static GroomingEstimate createDesignationGroomingEstimate(DesignationGroomingEstimateReq estimateReq, Long accountId) {
        return GroomingEstimate.builder()
                .reservedDate(estimateReq.getReservedDate())
                .desiredStyle(estimateReq.getDesiredStyle())
                .requirements(estimateReq.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .userId(accountId)
                .userImage(estimateReq.getUserImage())
                .nickname(estimateReq.getNickname())
                .address(estimateReq.getAddress())
                .petId(estimateReq.getPetId())
                .petImage(estimateReq.getPetImage())
                .petName(estimateReq.getName())
                .petBirth(estimateReq.getBirth())
                .petWeight(estimateReq.getWeight())
                .petSignificant(estimateReq.getSignificant())
                .groomerId(estimateReq.getGroomerId())
                .build();
    }

    public static List<EstimateInfo.PetInfo.Grooming> toInfos(List<GroomingEstimate> estimates) {
        List<EstimateInfo.PetInfo.Grooming> groomingInfos = new ArrayList<>();
        for (GroomingEstimate estimate : estimates) {
            groomingInfos.add(EstimateInfo.PetInfo.Grooming.builder()
                    .id(estimate.getGroomingEstimateId())
                    .name(estimate.getGroomerName())
                    .daengleMeter(estimate.getDaengleMeter())
                    .image(estimate.getGroomerImage())
                    .shopName(estimate.getShopName())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return groomingInfos;
    }

    public static GroomingEstimateDetail getGroomingEstimateDetail(GroomingEstimate estimate) {
        return GroomingEstimateDetail.builder()
                .image(estimate.getGroomerImage())
                .name(estimate.getGroomerName())
                .shopName(estimate.getShopName())
                .daengleMeter(estimate.getDaengleMeter())
                .introduction(estimate.getGroomerIntroduction())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .weight(estimate.getPetWeight())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }

}
