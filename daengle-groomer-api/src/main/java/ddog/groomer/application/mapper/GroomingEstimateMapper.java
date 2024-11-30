package ddog.groomer.application.mapper;

import ddog.domain.estimate.GroomingEstimate;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateInfo;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateDetail;

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

    public static GroomingEstimateDetail toGroomingEstimateDetail(GroomingEstimate groomingEstimate) {
        return GroomingEstimateDetail.builder()
                .userImage(groomingEstimate.getUserImage())
                .nickname(groomingEstimate.getNickname())
                .address(groomingEstimate.getAddress())
                .reservedDate(groomingEstimate.getReservedDate())
                .id(groomingEstimate.getPetId())
                .petImage(groomingEstimate.getPetImage())
                .name(groomingEstimate.getPetName())
                .birth(groomingEstimate.getPetBirth())
                .weight(groomingEstimate.getPetWeight())
                .significant(groomingEstimate.getPetSignificant())
                .desiredStyle(groomingEstimate.getDesiredStyle())
                .requirements(groomingEstimate.getRequirements())
                .build();
    }

}
