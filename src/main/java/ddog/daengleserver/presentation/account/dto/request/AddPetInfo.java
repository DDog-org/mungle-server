package ddog.daengleserver.presentation.account.dto.request;

import ddog.daengleserver.domain.account.enums.Breed;
import ddog.daengleserver.domain.account.enums.Gender;
import ddog.daengleserver.domain.account.enums.Weight;
import lombok.Getter;

@Getter
public class AddPetInfo {

    private String petImage;
    private String petName;
    private int petBirth;
    private Gender petGender;
    private Breed breed;
    private Boolean isNeutered;
    private Weight petWeight;
    private Boolean groomingExperience;
    private Boolean isBite;
    private String dislikeParts;
    private String petSignificant;

}
