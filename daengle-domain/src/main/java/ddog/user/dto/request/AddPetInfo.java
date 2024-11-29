package ddog.user.dto.request;

import ddog.pet.Breed;
import ddog.pet.Gender;
import ddog.pet.Weight;
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
