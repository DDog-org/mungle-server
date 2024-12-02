package ddog.user.presentation.account.dto;

import ddog.domain.pet.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifyPetInfo {

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
    private List<Part> dislikeParts;
    private List<SignificantTag> significantTags;
    private String significant;

}
