package ddog.user.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.user.presentation.estimate.dto.AccountInfo;
import ddog.user.presentation.estimate.dto.EstimateInfo;
import ddog.user.presentation.estimate.dto.GroomingEstimateDetail;
import ddog.user.presentation.estimate.dto.GroomingEstimateReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroomingEstimateMapper {

    public static GroomingEstimate createGeneralGroomingEstimate(GroomingEstimateReq estimateReq, Long accountId) {
        return GroomingEstimate.builder()
                .userId(accountId)
                .petId(estimateReq.getPetId())
                .address(estimateReq.getAddress())
                .reservedDate(estimateReq.getReservedDate())
                .desiredStyle(estimateReq.getDesiredStyle())
                .requirements(estimateReq.getRequirements())
                .proposal(Proposal.GENERAL)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static GroomingEstimate createDesignationGroomingEstimate(GroomingEstimateReq estimateReq, Long accountId) {
        return GroomingEstimate.builder()
                .groomerId(estimateReq.getGroomerId())
                .userId(accountId)
                .address(estimateReq.getAddress())
                .reservedDate(estimateReq.getReservedDate())
                .desiredStyle(estimateReq.getDesiredStyle())
                .requirements(estimateReq.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static EstimateInfo.PetInfo.Grooming toGrooming(GroomingEstimate estimate, Groomer groomer) {
        return EstimateInfo.PetInfo.Grooming.builder()
                .groomingEstimateId(estimate.getGroomingEstimateId())
                .name(groomer.getGroomerName())
                .daengleMeter(groomer.getDaengleMeter())
                .image(groomer.getGroomerImage())
                .shopName(groomer.getShopName())
                .reservedDate(estimate.getReservedDate())
                .build();
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

    public static AccountInfo.Grooming toGroomingInfo(Groomer groomer, User user) {
        List<AccountInfo.PetInfo> petInfos = toPetInfos(user);

        return AccountInfo.Grooming.builder()
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .shopName(groomer.getShopName())
                .address(user.getAddress())
                .petInfos(petInfos)
                .build();
    }

    public static AccountInfo.Care toCareInfo(Vet vet, User user) {
        List<AccountInfo.PetInfo> petInfos = toPetInfos(user);

        return AccountInfo.Care.builder()
                .vetImage(vet.getVetImage())
                .vetName(vet.getVetName())
                .address(user.getAddress())
                .petInfos(petInfos)
                .build();
    }

    private static List<AccountInfo.PetInfo> toPetInfos(User user) {
        List<AccountInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : user.getPets()) {
            petInfos.add(AccountInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .image(pet.getPetImage())
                    .name(pet.getPetName())
                    .build());
        }
        return petInfos;
    }
}
