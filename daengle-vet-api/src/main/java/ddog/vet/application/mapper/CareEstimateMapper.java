package ddog.vet.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.CreatePendingEstimateReq;

import java.time.LocalDateTime;

public class CareEstimateMapper {

    public static EstimateInfo.Content mapToContent(CareEstimate estimate, User user, Pet pet) {
        return EstimateInfo.Content.builder()
                .id(estimate.getEstimateId())
                .image(user.getUserImage())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getPetSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateDetail mapToEstimateDetail(CareEstimate estimate, User user, Pet pet) {
        return EstimateDetail.builder()
                .userImage(user.getUserImage())
                .nickname(user.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .proposal(estimate.getProposal())
                .petId(pet.getPetId())
                .petImage(pet.getPetImage())
                .petName(pet.getPetName())
                .age(pet.getAge())
                .weight(pet.getPetWeight())
                .significant(pet.getPetSignificant())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }

    public static CareEstimate createPendingEstimate(CreatePendingEstimateReq request, Vet vet, CareEstimate estimate) {
        return CareEstimate.builder()
                .parentId(estimate.getEstimateId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .vetId(vet.getAccountId())
                .address(estimate.getAddress())
                .reservedDate(request.getReservedDate())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .status(EstimateStatus.PENDING)
                .proposal(estimate.getProposal())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .diagnosis(request.getDiagnosis())
                .cause(request.getCause())
                .treatment(request.getTreatment())
                .build();
    }

}
