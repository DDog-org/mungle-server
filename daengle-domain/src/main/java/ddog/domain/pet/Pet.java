package ddog.domain.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static java.time.Year.now;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private Long petId;
    private Long accountId;
    private Gender gender;
    private String name;
    private String imageUrl;
    private String significant;
    private int birth;
    private Weight weight;
    private Breed breed;
    private Boolean isNeutered;
    private Boolean groomingExperience;
    private Boolean isBite;
    private List<Part> dislikeParts;
    private List<SignificantTag> significantTags;

    public static void validatePetName(String name) {
        if (name == null || name.length() < 2 || name.length() > 10) {
            throw new IllegalArgumentException("Invalid pet name: must be 2-10 characters.");
        }
    }

    public static void validatePetBirth(int birth) {
        int currentYear = now().getValue();
        if (birth < currentYear - 30 || birth > currentYear) {
            throw new IllegalArgumentException("Invalid pet birth year: must be within last 30 years.");
        }
    }

    public static void validatePetGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Invalid pet gender: must be MALE or FEMALE.");
        }
    }

    public static void validatePetWeight(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Invalid pet weight: must be SMALL, MEDIUM, or LARGE.");
        }
    }

    public static void validateBreed(Breed breed) {
        if (breed == null) {
            throw new IllegalArgumentException("Invalid breed: must be a valid value.");
        }
    }

    public static void validatePetDislikeParts(List<Part> dislikeParts) {
        for (Part part : dislikeParts) {
            if (part == null) {
                throw new IllegalArgumentException("Invalid dislike part: element cannot be null.");
            }
        }
    }

    public static void validateSignificantTags(List<SignificantTag> significantTags) {
        for (SignificantTag tag : significantTags) {
            if (tag == null) {
                throw new IllegalArgumentException("Invalid significant tag: element cannot be null.");
            }
        }
    }

    public int getAge() {
        int currentYear = LocalDate.now().getYear();

        return currentYear - birth + 1;
    }
}