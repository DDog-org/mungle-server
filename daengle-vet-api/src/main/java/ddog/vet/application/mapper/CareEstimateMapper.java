package ddog.vet.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.vet.Vet;
import ddog.vet.presentation.estimate.dto.CareEstimateDetail;
import ddog.vet.presentation.estimate.dto.CareEstimateInfo;
import ddog.vet.presentation.estimate.dto.CareEstimateReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CareEstimateMapper {

    public static CareEstimateInfo findCareEstimateInfo(
            List<CareEstimate> generalEstimates,
            List<CareEstimate> designationEstimates
    ) {
        List<CareEstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<CareEstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // careEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(CareEstimateInfo.Content::getId));
        return CareEstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private static List<CareEstimateInfo.Content> estimatesToContents(List<CareEstimate> estimates) {
        List<CareEstimateInfo.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            contents.add(CareEstimateInfo.Content.builder()
                    .id(estimate.getCareEstimateId())
                    .image(estimate.getUserImage())
                    .nickname(estimate.getNickname())
                    .proposal(estimate.getProposal())
                    .significant(estimate.getPetSignificant())
                    .reservedDate(estimate.getReservedDate())
                    .build());
        }
        return contents;
    }

    public static CareEstimateDetail toUserCareEstimateDetail(CareEstimate estimate) {
        return CareEstimateDetail.builder()
                .userImage(estimate.getUserImage())
                .nickname(estimate.getNickname())
                .address(estimate.getAddress())
                .reservedDate(estimate.getReservedDate())
                .id(estimate.getPetId())
                .petImage(estimate.getPetImage())
                .birth(estimate.getPetBirth())
                .weight(estimate.getPetWeight())
                .significant(estimate.getPetSignificant())
                .name(estimate.getPetName())
                .symptoms(estimate.getSymptoms())
                .requirements(estimate.getRequirements())
                .build();
    }

    public static CareEstimate createVetCareEstimate(CareEstimateReq request, Vet vet, CareEstimate estimate) {
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
