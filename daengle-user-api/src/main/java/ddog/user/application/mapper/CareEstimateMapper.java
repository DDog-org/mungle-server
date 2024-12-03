package ddog.user.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.domain.vet.Vet;
import ddog.user.presentation.estimate.dto.CareEstimateDetail;
import ddog.user.presentation.estimate.dto.CareEstimateReq;
import ddog.user.presentation.estimate.dto.EstimateInfo;

import java.time.LocalDateTime;

public class CareEstimateMapper {

    public static CareEstimate createGeneralCareEstimate(CareEstimateReq request, Long accountId) {
        return CareEstimate.builder()
                .userId(accountId)
                .petId(request.getPetId())
                .address(request.getAddress())
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CareEstimate createDesignationCareEstimate(CareEstimateReq request, Long accountId) {
        return CareEstimate.builder()
                .userId(accountId)
                .vetId(request.getVetId())
                .petId(request.getPetId())
                .address(request.getAddress())
                .reservedDate(request.getReservedDate())
                .symptoms(request.getSymptoms())
                .requirements(request.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static EstimateInfo.PetInfo.Care toCare(CareEstimate estimates, Vet vet) {
        return EstimateInfo.PetInfo.Care.builder()
                .careEstimateId(estimate.getCareEstimateId())
                .name(estimate.getVetName())
                .daengleMeter(estimate.getDaengleMeter())
                .image(estimate.getVetImage())
                .reservedDate(estimate.getReservedDate())
                .build();
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
