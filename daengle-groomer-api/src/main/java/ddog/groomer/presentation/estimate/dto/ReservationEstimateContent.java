package ddog.groomer.presentation.estimate.dto;

import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReservationEstimateContent {
    private Long userId;
    private String userProfile;
    private String userName;
    private String partnerAddress;
    private LocalDateTime reservedDate;

    private Long petId;
    private String petProfile;
    private String petName;
    private Integer petAge;
    private Weight petWeight;
    private List<String> dislikeParts;
    private List<String> significantTags;
    private String etcSignificants;

    @Getter
    @Builder
    public static class toVetPetInfo {
        private String symptoms;
        private String requirements;

        private String treatment;
        private String cause;
        private String diagnosis;
    }

    @Getter
    @Builder
    public static class toGroomerPetInfo {
        private String desiredStyle;
        private String requirements;

        private String overallOpinion;
    }

}
