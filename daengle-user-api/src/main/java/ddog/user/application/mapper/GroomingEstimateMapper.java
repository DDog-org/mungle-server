package ddog.user.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.Proposal;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.user.presentation.estimate.dto.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroomingEstimateMapper {

    public static UserInfo.Grooming mapToUserInfo(User user) {
        List<UserInfo.PetInfo> petInfos = toPetInfos(user);

        return UserInfo.Grooming.builder()
                .address(user.getAddress())
                .petInfos(petInfos)
                .build();
    }

    public static UserInfo.Grooming mapToUserInfoWithGroomer(Groomer groomer, User user) {
        List<UserInfo.PetInfo> petInfos = toPetInfos(user);

        return UserInfo.Grooming.builder()
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .shopName(groomer.getShopName())
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

    public static GroomingEstimate createNewGeneralGroomingEstimate(CreateNewGroomingEstimateReq estimateReq, Long accountId) {
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
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static GroomingEstimate createNewDesignationGroomingEstimate(CreateNewGroomingEstimateReq estimateReq, Long accountId) {
        return GroomingEstimate.builder()
                .groomerId(estimateReq.getGroomerId())
                .userId(accountId)
                .petId(estimateReq.getPetId())
                .address(estimateReq.getAddress())
                .reservedDate(estimateReq.getReservedDate())
                .desiredStyle(estimateReq.getDesiredStyle())
                .requirements(estimateReq.getRequirements())
                .proposal(Proposal.DESIGNATION)
                .status(EstimateStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static EstimateInfo.Grooming.Content mapToEstimateInfo(GroomingEstimate estimate, Groomer groomer) {
        return EstimateInfo.Grooming.Content.builder()
                .id(estimate.getEstimateId())
                .name(groomer.getGroomerName())
                .daengleMeter(groomer.getDaengleMeter())
                .imageURL(groomer.getGroomerImage())
                .shopName(groomer.getShopName())
                .keywords(groomer.getKeywords())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static UserEstimate.Grooming mapToUserGroomingEstimate(GroomingEstimate estimate, Pet pet) {
        return UserEstimate.Grooming.builder()
                .id(estimate.getEstimateId())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .proposal(estimate.getProposal())
                .petImage(pet.getPetImage())
                .petName(pet.getPetName())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static GroomingEstimateDetail mapToEstimateDetail(GroomingEstimate estimate, Groomer groomer, Pet pet) {
        return GroomingEstimateDetail.builder()
                .groomingEstimateId(estimate.getEstimateId())
                .groomerId(groomer.getGroomerId())
                .image(groomer.getGroomerImage())
                .name(groomer.getGroomerName())
                .shopName(groomer.getShopName())
                .daengleMeter(groomer.getDaengleMeter())
                .proposal(estimate.getProposal())
                .introduction(groomer.getGroomerIntroduction())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .weight(pet.getPetWeight())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }
}
