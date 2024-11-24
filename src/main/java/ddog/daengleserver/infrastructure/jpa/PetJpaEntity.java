package ddog.daengleserver.infrastructure.jpa;

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
    private String petName;
    private String petImage;
    private String petSignificant;
    private int petBirth;

    @Enumerated(EnumType.STRING)
    private Weight petWeight;

    public Pet toModel() {
        return Pet.builder()
                .petId(petId)
                .userId(userId)
                .petName(petName)
                .petImage(petImage)
                .petSignificant(petSignificant)
                .petBirth(petBirth)
                .petWeight(petWeight)
                .build();
    }
}
