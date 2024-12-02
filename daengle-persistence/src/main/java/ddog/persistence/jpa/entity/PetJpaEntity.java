package ddog.persistence.jpa.entity;

import ddog.domain.pet.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ElementCollection // 싫어하는 부위 리스트
    @CollectionTable(name = "dislike_parts", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "dislike_part")
    private List<Part> dislikeParts;

    @ElementCollection // 특이사항 태그 리스트
    @CollectionTable(name = "significant_tags", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "significant_tag")
    private List<SignificantTag> significantTags;

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
                .significantTags(significantTags)
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
                .significantTags(pet.getSignificantTags())
                .build();
    }
}
