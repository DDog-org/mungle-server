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

    public static EstimateInfo.General.Content mapToGeneralContent(CareEstimate estimate, User user, Pet pet) {
        return EstimateInfo.General.Content.builder()
                .id(estimate.getEstimateId())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateInfo.Designation.Content mapToDesignationContent(CareEstimate estimate, User user, Pet pet) {
        return EstimateInfo.Designation.Content.builder()
                .id(estimate.getEstimateId())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateDetail mapToEstimateDetail(CareEstimate estimate, User user, Pet pet) {
        return EstimateDetail.builder()
                .userImageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .proposal(estimate.getProposal())
                .petId(pet.getPetId())
                .petImageUrl(pet.getImageUrl())
                .petName(pet.getName())
                .age(pet.getAge())
                .weight(pet.getWeight())
                .significant(pet.getSignificant())
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
