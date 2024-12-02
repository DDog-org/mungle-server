package ddog.user.presentation.account.dto;

import ddog.domain.pet.Breed;
import ddog.domain.pet.Gender;
import ddog.domain.pet.Weight;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetInfo {
    List<Detail> petDetails;

    @Getter
    @Builder
    public static class Detail {
        private Long id;
        private String image;
        private String name;
        private int birth;
        private Gender gender;
        private Breed breed;
        private Boolean isNeutered;
        private Weight weight;
        private Boolean groomingExperience;
        private Boolean isBite;
        private String[] dislikeParts;
        private String significant;
    }
}
