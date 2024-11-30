package ddog.domain.estimate;

import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Weight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
