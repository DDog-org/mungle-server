package ddog.groomer.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.groomer.presentation.estimate.dto.EstimateDetail;
import ddog.groomer.presentation.estimate.dto.EstimateInfo;
import ddog.groomer.presentation.estimate.dto.CreatePendingEstimateReq;

import java.time.LocalDateTime;

public class GroomingEstimateMapper {

    public static EstimateInfo.Content mapToContent(GroomingEstimate estimate, User user, Pet pet) {
        return EstimateInfo.Content.builder()
                .id(estimate.getEstimateId())
                .image(user.getUserImage())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getPetSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateDetail mapToEstimateDetail(GroomingEstimate estimate, User user, Pet pet) {
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
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }

    public static GroomingEstimate createPendingEstimate(
            CreatePendingEstimateReq request,
            Groomer groomer,
            GroomingEstimate estimate) {
        return GroomingEstimate.builder()
                .parentId(estimate.getEstimateId())
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .groomerId(groomer.getAccountId())
                .address(estimate.getAddress())
                .reservedDate(request.getReservedDate())
                .requirements(estimate.getRequirements())
                .status(EstimateStatus.PENDING)
                .proposal(estimate.getProposal())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(request.getOverallOpinion())
                .build();
    }

}
