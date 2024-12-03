package ddog.groomer.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.groomer.presentation.estimate.dto.EstimateDetail;
import ddog.groomer.presentation.estimate.dto.EstimateInfo;
import ddog.groomer.presentation.estimate.dto.EstimateReq;

import java.time.LocalDateTime;

public class GroomingEstimateMapper {

    public static EstimateInfo.Content estimateToContent(GroomingEstimate estimate, User user, Pet pet) {
        return EstimateInfo.Content.builder()
                .id(estimate.getGroomingEstimateId())
                .image(user.getUserImage())
                .nickname(user.getNickname())
                .proposal(estimate.getProposal())
                .significant(pet.getPetSignificant())
                .reservedDate(estimate.getReservedDate())
                .build();
    }

    public static EstimateDetail toEstimateDetail(GroomingEstimate estimate, User user, Pet pet) {
        return EstimateDetail.builder()
                .userImage(user.getUserImage())
                .nickname(user.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .petId(pet.getPetId())
                .petImage(pet.getPetImage())
                .petName(pet.getPetName())
                .birth(pet.getPetBirth())
                .weight(pet.getPetWeight())
                .significant(pet.getPetSignificant())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static GroomingEstimate createGroomingEstimate(
            EstimateReq request,
            Groomer groomer,
            GroomingEstimate estimate) {
        return GroomingEstimate.builder()
                .userId(estimate.getUserId())
                .petId(estimate.getPetId())
                .groomerId(groomer.getGroomerId())
                .address(estimate.getAddress())
                .reservedDate(request.getReservedDate())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .overallOpinion(request.getOverallOpinion())
                .proposal(estimate.getProposal())
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
