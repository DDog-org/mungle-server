package ddog.domain.user.dto.request;

import ddog.domain.pet.Breed;
import ddog.domain.pet.Gender;
import ddog.domain.pet.Weight;
import lombok.Getter;

@Getter
public class AddPetInfo {

    private String image;
    private String name;
    private int birth;
    private Gender gender;
    private Breed breed;
    private Boolean isNeutered;
    private Weight weight;
    private Boolean groomingExperience;
    private Boolean isBite;
    private String dislikeParts;
    private String significant;

}
