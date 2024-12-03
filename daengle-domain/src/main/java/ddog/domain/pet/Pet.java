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

    public static void validatePetName(String petName) {
        if (petName == null || petName.length() < 2 || petName.length() > 10) {
            throw new IllegalArgumentException("Invalid pet name: must be 2-10 characters.");
        }
    }

    public static void validatePetBirth(int petBirth) {
        int currentYear = java.time.Year.now().getValue();
        if (petBirth < currentYear - 30 || petBirth > currentYear) {
            throw new IllegalArgumentException("Invalid pet birth year: must be within last 30 years.");
        }
    }

    public static void validatePetGender(Gender petGender) {
        if (petGender == null) {
            throw new IllegalArgumentException("Invalid pet gender: must be MALE or FEMALE.");
        }
    }

    public static void validatePetWeight(Weight petWeight) {
        if (petWeight == null) {
            throw new IllegalArgumentException("Invalid pet weight: must be SMALL, MEDIUM, or LARGE.");
        }
    }

    public static void validateBreed(Breed breed) {
        if (breed == null) {
            throw new IllegalArgumentException("Invalid breed: must be a valid value.");
        }
    }
}