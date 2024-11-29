package ddog.user.dto.response;

import ddog.pet.Breed;
import ddog.pet.Gender;
import ddog.pet.Weight;
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
