package ddog.vet.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateReq;

import java.time.LocalDateTime;

public class CareEstimateMapper {

    public static EstimateInfo.Content estimateToContent(CareEstimate estimate, User user, Pet pet) {
        return EstimateInfo.Content.builder()
                .id(estimate.getCareEstimateId())
                .image(user.getUserImage())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getPetSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateDetail toUserCareEstimateDetail(CareEstimate estimate, User user, Pet pet) {
        return EstimateDetail.builder()
                .userImage(user.getUserImage())
                .nickname(user.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .petId(pet.getPetId())
                .petImage(pet.getPetImage())
                .birth(pet.getPetBirth())
                .weight(pet.getPetWeight())
                .significant(pet.getPetSignificant())
                .petName(pet.getPetName())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static CareEstimate createVetCareEstimate(EstimateReq request, Vet vet, CareEstimate estimate) {
        return CareEstimate.builder()
                .reservedDate(request.getReservedDate())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .proposal(estimate.getProposal())
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .diagnosis(request.getDiagnosis())
                .cause(request.getCause())
                .treatment(request.getTreatment())
                .userId(estimate.getUserId())
                .userImage(estimate.getUserImage())
                .nickname(estimate.getNickname())
                .address(estimate.getAddress())
                .petId(estimate.getPetId())
                .petImage(estimate.getPetImage())
                .petName(estimate.getPetName())
                .petBirth(estimate.getPetBirth())
                .petWeight(estimate.getPetWeight())
                .petSignificant(estimate.getPetSignificant())
                .vetId(vet.getVetId())
                .daengleMeter(vet.getDaengleMeter())
                .vetImage(vet.getVetImage())
                .vetName(vet.getVetName())
                .vetIntroduction(vet.getVetIntroduction())
                .build();
    }

}
