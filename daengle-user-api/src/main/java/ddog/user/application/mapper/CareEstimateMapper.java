package ddog.user.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.user.presentation.estimate.dto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CareEstimateMapper {

    public static UserInfo.Care mapToUserInfo(User user) {
        List<UserInfo.PetInfo> petInfos = toPetInfos(user);

        return UserInfo.Care.builder()
                .address(user.getAddress())
                .petInfos(petInfos)
                .build();
    }

    public static UserInfo.Care mapToUserInfoWithVet(Vet vet, User user) {
        List<UserInfo.PetInfo> petInfos = toPetInfos(user);

        return UserInfo.Care.builder()
                .vetImage(vet.getVetImage())
                .vetName(vet.getVetName())
                .address(user.getAddress())
                .petInfos(petInfos)
                .build();
    }

    private static List<UserInfo.PetInfo> toPetInfos(User user) {
        List<UserInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : user.getPets()) {
            petInfos.add(UserInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .image(pet.getPetImage())
                    .name(pet.getPetName())
                    .build());
        }
        return petInfos;
    }

    public static CareEstimate createNewGeneralCareEstimate(CreateNewCareEstimateReq request, Long accountId) {
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
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static CareEstimate createNewDesignationCareEstimate(CreateNewCareEstimateReq request, Long accountId) {
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
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static EstimateInfo.Care.Content mapToEstimateInfo(CareEstimate estimate, Vet vet) {
        return EstimateInfo.Care.Content.builder()
                .id(estimate.getEstimateId())
                .name(vet.getVetName())
                .daengleMeter(vet.getDaengleMeter())
                .imageURL(vet.getVetImage())
                .keywords(vet.getKeywords())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static UserEstimate.Care mapToUserCareEstimate(CareEstimate estimate, Pet pet) {
        return UserEstimate.Care.builder()
                .id(estimate.getEstimateId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .proposal(estimate.getProposal())
                .petImage(pet.getPetImage())
                .petName(pet.getPetName())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static CareEstimateDetail mapToEstimateDetail(CareEstimate estimate, Vet vet, Pet pet) {
        return CareEstimateDetail.builder()
                .careEstimateId(estimate.getEstimateId())
                .vetId(vet.getVetId())
                .image(vet.getVetImage())
                .name(vet.getVetName())
                .daengleMeter(vet.getDaengleMeter())
                .proposal(estimate.getProposal())
                .introduction(vet.getVetIntroduction())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }
}
