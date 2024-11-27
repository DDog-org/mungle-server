package ddog.daengleserver.presentation.account.dto.response;

import ddog.daengleserver.domain.Breed;
import ddog.daengleserver.domain.Gender;
import ddog.daengleserver.domain.Weight;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetInfos {
    List<Details> petDetails;

    @Getter
    @Builder
    public static class Details {
        private Long petId;
        private String petImage;
        private String petName;
        private int petBirth;
        private Gender petGender;
        private Breed breed;
        private Boolean isNeutered;
        private Weight petWeight;
        private Boolean groomingExperience;
        private Boolean isBite;
        private String[] dislikeParts;
        private String petSignificant;

    }
}
