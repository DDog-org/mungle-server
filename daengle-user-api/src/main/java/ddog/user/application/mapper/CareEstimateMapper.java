package ddog.user.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.user.presentation.estimate.dto.CareEstimateDetail;
import ddog.user.presentation.estimate.dto.DesignationCareEstimateReq;
import ddog.user.presentation.estimate.dto.EstimateInfo;
import ddog.user.presentation.estimate.dto.GeneralCareEstimateReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CareEstimateMapper {

    public static CareEstimate createGeneralCareEstimate(GeneralCareEstimateReq estimateReq, Long accountId) {
        return CareEstimate.builder()
                .reservedDate(estimateReq.getReservedDate())
                .symptoms(estimateReq.getSymptoms())
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

    public static CareEstimate createDesignationCareEstimate(DesignationCareEstimateReq estimateReq, Long accountId) {
        return CareEstimate.builder()
                .reservedDate(estimateReq.getReservedDate())
                .symptoms(estimateReq.getSymptoms())
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
                .vetId(estimateReq.getVetId())
                .build();
    }

    public static List<EstimateInfo.PetInfo.Care> toInfos(List<CareEstimate> estimates) {
        List<EstimateInfo.PetInfo.Care> careInfos = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            careInfos.add(EstimateInfo.PetInfo.Care.builder()
                    .id(estimate.getCareEstimateId())
                    .name(estimate.getVetName())
                    .daengleMeter(estimate.getDaengleMeter())
                    .image(estimate.getVetImage())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return careInfos;
    }

    public static CareEstimateDetail getCareEstimateDetail(CareEstimate estimate) {
        return CareEstimateDetail.builder()
                .image(estimate.getVetImage())
                .name(estimate.getVetName())
                .daengleMeter(estimate.getDaengleMeter())
                .introduction(estimate.getVetIntroduction())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }
}
