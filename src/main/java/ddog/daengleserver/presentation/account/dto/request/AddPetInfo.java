package ddog.daengleserver.presentation.account.dto.request;

import ddog.account.enums.Breed;
import ddog.account.enums.Gender;
import ddog.account.enums.Weight;
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
