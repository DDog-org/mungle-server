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

    public static CareEstimate createGeneralCareEstimate(GeneralCareEstimateReq request, Long accountId) {
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

    public static CareEstimate createDesignationCareEstimate(DesignationCareEstimateReq request, Long accountId) {
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
                .petId(request.getPetId())
                .petImage(request.getPetImage())
                .petName(request.getName())
                .petBirth(request.getBirth())
                .petWeight(request.getWeight())
                .petSignificant(request.getSignificant())
                .vetId(request.getVetId())
                .build();
    }

    public static List<EstimateInfo.PetInfo.Care> toInfos(List<CareEstimate> careEstimates) {
        List<EstimateInfo.PetInfo.Care> careInfos = new ArrayList<>();
        for (CareEstimate careEstimate : careEstimates) {
            careInfos.add(EstimateInfo.PetInfo.Care.builder()
                    .id(careEstimate.getCareEstimateId())
                    .name(careEstimate.getVetName())
                    .daengleMeter(careEstimate.getDaengleMeter())
                    .image(careEstimate.getVetImage())
                    .reservedDate(careEstimate.getReservedDate())
                    .build());
        }
        return careInfos;
    }

    public static CareEstimateDetail getCareEstimateDetail(CareEstimate careEstimate) {
        return CareEstimateDetail.builder()
                .image(careEstimate.getVetImage())
                .name(careEstimate.getVetName())
                .daengleMeter(careEstimate.getDaengleMeter())
                .introduction(careEstimate.getVetIntroduction())
                .address(careEstimate.getAddress())
                .reservedDate(careEstimate.getReservedDate())
                .diagnosis(careEstimate.getDiagnosis())
                .cause(careEstimate.getCause())
                .treatment(careEstimate.getTreatment())
                .build();
    }
}
