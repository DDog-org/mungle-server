package ddog.groomer.application.mapper;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateReq;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateInfo;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroomingEstimateMapper {

    public static GroomingEstimateInfo toGroomingEstimateInfo(
            List<GroomingEstimate> generalEstimates,
            List<GroomingEstimate> designationEstimates
    ) {
        List<GroomingEstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<GroomingEstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // groomingEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(GroomingEstimateInfo.Content::getId));

        return GroomingEstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private static List<GroomingEstimateInfo.Content> estimatesToContents(List<GroomingEstimate> estimates) {
        List<GroomingEstimateInfo.Content> contents = new ArrayList<>();
        for (GroomingEstimate estimate : estimates) {
            contents.add(GroomingEstimateInfo.Content.builder()
                    .id(estimate.getGroomingEstimateId())
                    .image(estimate.getUserImage())
                    .nickname(estimate.getNickname())
                    .proposal(estimate.getProposal())
                    .significant(estimate.getPetSignificant())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return contents;
    }

    public static GroomingEstimateDetail toGroomingEstimateDetail(GroomingEstimate estimate) {
        return GroomingEstimateDetail.builder()
                .userImage(estimate.getUserImage())
                .nickname(estimate.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .id(estimate.getPetId())
                .petImage(estimate.getPetImage())
                .name(estimate.getPetName())
                .birth(estimate.getPetBirth())
                .weight(estimate.getPetWeight())
                .significant(estimate.getPetSignificant())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static GroomingEstimate createGroomerGroomingEstimate(
            GroomingEstimateReq request,
            Groomer groomer,
            GroomingEstimate estimate)
    {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(estimate.getDesiredStyle())
                .requirements(estimate.getRequirements())
                .proposal(estimate.getProposal())
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
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
                .groomerId(groomer.getGroomerId())
                .daengleMeter(groomer.getDaengleMeter())
                .overallOpinion(request.getOverallOpinion())
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .build();
    }

}
