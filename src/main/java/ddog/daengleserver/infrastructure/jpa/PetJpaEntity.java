package ddog.daengleserver.infrastructure.jpa;

import ddog.daengleserver.domain.Pet;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long ownerId;
    private String petName;
    private String petImage;
    private String petSignificant;

    public Pet toModel() {
        return Pet.builder()
                .petId(this.petId)
                .ownerId(this.ownerId)
                .petName(this.petName)
                .petImage(this.petImage)
                .petSignificant(this.petSignificant)
                .build();
    }
}
