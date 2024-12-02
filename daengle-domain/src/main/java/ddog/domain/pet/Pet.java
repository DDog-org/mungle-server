package ddog.domain.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private Long petId;
    private Long accountId;
    private Gender petGender;
    private String petName;
    private String petImage;
    private String petSignificant;
    private int petBirth;
    private Weight petWeight;
    private Breed breed;
    private Boolean isNeutered;
    private Boolean groomingExperience;
    private Boolean isBite;
    private List<Part> dislikeParts;
    private List<SignificantTag> significantTags;

}