package ddog.vet.presentation.estimate.dto;


import ddog.domain.pet.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PetInfo {
        private Long petId;
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
