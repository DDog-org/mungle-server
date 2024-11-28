package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.account.Pet;
import ddog.daengleserver.domain.account.enums.Breed;
import ddog.daengleserver.domain.account.enums.Gender;
import ddog.daengleserver.domain.account.enums.Weight;
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

    @Column(name = "account_id")
    private Long accountId;

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
    private Boolean isNeutered;
    private Boolean groomingExperience;
    private Boolean isBite;
    private String dislikeParts;

    public Pet toModel() {
        return Pet.builder()
                .petId(petId)
                .accountId(accountId)
                .petGender(petGender)
                .petName(petName)
                .petImage(petImage)
                .petSignificant(petSignificant)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .breed(breed)
                .isNeutered(isNeutered)
                .groomingExperience(groomingExperience)
                .isBite(isBite)
                .dislikeParts(dislikeParts)
                .build();
    }

    public static PetJpaEntity from(Pet pet) {
        return PetJpaEntity.builder()
                .petId(pet.getPetId())
                .accountId(pet.getAccountId())
                .petGender(pet.getPetGender())
                .petName(pet.getPetName())
                .petImage(pet.getPetImage())
                .petSignificant(pet.getPetSignificant())
                .petBirth(pet.getPetBirth())
                .petWeight(pet.getPetWeight())
                .breed(pet.getBreed())
                .isNeutered(pet.getIsNeutered())
                .groomingExperience(pet.getGroomingExperience())
                .isBite(pet.getIsBite())
                .dislikeParts(pet.getDislikeParts())
                .build();
    }
}
