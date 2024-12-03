package ddog.user.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.user.presentation.estimate.dto.CareEstimateDetail;
import ddog.user.presentation.estimate.dto.CareEstimateReq;
import ddog.user.presentation.estimate.dto.EstimateInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CareEstimateMapper {

    public static CareEstimate createGeneralCareEstimate(CareEstimateReq request, Long accountId) {
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

    public static CareEstimate createDesignationCareEstimate(CareEstimateReq request, Long accountId) {
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
                .petId(request.getId())
                .petImage(request.getPetImage())
                .petName(request.getName())
                .petBirth(request.getBirth())
                .petWeight(request.getWeight())
                .petSignificant(request.getSignificant())
                .vetId(request.getVetId())
                .build();
    }

    public static List<EstimateInfo.PetInfo.Care> toInfos(List<CareEstimate> estimates) {
        List<EstimateInfo.PetInfo.Care> careInfos = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            careInfos.add(EstimateInfo.PetInfo.Care.builder()
                    .careEstimateId(estimate.getCareEstimateId())
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
