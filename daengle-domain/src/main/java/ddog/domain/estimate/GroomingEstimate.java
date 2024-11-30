package ddog.domain.estimate;

import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.domain.estimate.dto.response.GroomingEstimateInfo;
import ddog.domain.estimate.dto.response.UserGroomingEstimateDetail;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Weight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingEstimate {

    private Long groomingEstimateId;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;

    private Long userId;
    private String userImage;
    private String nickname;
    private String address;

    private Long petId;
    private String petImage;
    private String petName;
    private int petBirth;
    private Weight petWeight;
    private String petSignificant;

    private Long groomerId;
    private int daengleMeter;
    private String overallOpinion;
    private String groomerImage;
    private String groomerName;
    private String shopName;
    private String groomerIntroduction;

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

    public UserGroomingEstimateDetail toUserGroomingEstimateDetail() {
        return UserGroomingEstimateDetail.builder()
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .reservedDate(reservedDate)
                .id(petId)
                .petImage(petImage)
                .name(petName)
                .birth(petBirth)
                .weight(petWeight)
                .significant(petSignificant)
                .desiredStyle(desiredStyle)
                .requirements(requirements)
                .build();
    }

    public GroomingEstimate createGroomerGroomingEstimate(GroomerGroomingEstimateReq request, Groomer groomer) {
        return GroomingEstimate.builder()
                .reservedDate(request.getReservedDate())
                .desiredStyle(desiredStyle)
                .requirements(requirements)
                .proposal(proposal)
                .status(EstimateStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .userImage(userImage)
                .nickname(nickname)
                .address(address)
                .petId(petId)
                .petImage(petImage)
                .petName(petName)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .petSignificant(petSignificant)
                .groomerId(groomer.getGroomerId())
                .daengleMeter(groomer.getDaengleMeter())
                .overallOpinion(request.getOverallOpinion())
                .groomerImage(groomer.getGroomerImage())
                .groomerName(groomer.getGroomerName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .build();
    }

}
