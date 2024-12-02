package ddog.domain.estimate;

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
public class CareEstimate {

    private Long careEstimateId;
    private LocalDateTime reservedDate;
    private String symptoms;
    private String requirements;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private String diagnosis;
    private String cause;
    private String treatment;

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

    private Long vetId;
    private int daengleMeter;
    private String vetImage;
    private String vetName;
    private String vetIntroduction;

}
