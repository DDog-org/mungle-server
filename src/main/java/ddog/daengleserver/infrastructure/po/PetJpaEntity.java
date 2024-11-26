package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Breed;
import ddog.daengleserver.domain.Gender;
import ddog.daengleserver.domain.Pet;
import ddog.daengleserver.domain.Weight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Pets")
public class PetJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Gender petGender;
    private String petName;
    private String petImage;
    private String petSignificant;
    private int petBirth;

    @Enumerated(EnumType.STRING)
    private Weight petWeight;
    @Enumerated(EnumType.STRING)
    private Breed breed;

    public Pet toModel() {
        return Pet.builder()
                .petId(petId)
                .userId(userId)
                .petGender(petGender)
                .petName(petName)
                .petImage(petImage)
                .petSignificant(petSignificant)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .breed(breed)
                .build();
    }

    public static PetJpaEntity from(Pet pet) {
        return PetJpaEntity.builder()
                .petId(pet.getPetId())
                .userId(pet.getUserId())
                .petGender(pet.getPetGender())
                .petName(pet.getPetName())
                .petImage(pet.getPetImage())
                .petSignificant(pet.getPetSignificant())
                .petBirth(pet.getPetBirth())
                .petWeight(pet.getPetWeight())
                .breed(pet.getBreed())
                .build();
    }
}
