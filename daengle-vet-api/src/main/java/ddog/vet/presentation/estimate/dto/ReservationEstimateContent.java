package ddog.vet.presentation.estimate.dto;

import ddog.domain.pet.Part;
import ddog.domain.pet.SignificantTag;
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
    private List<Part> dislikeParts;
    private List<SignificantTag> significantTags;
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
}
