package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Vet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Vets")
public class VetJpaEntity {

    @Id
    private Long vetId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpaEntity account;
    private String vetName;
    private String vetImage;
    private String address;
    private String vetIntroduction;

    public static VetJpaEntity from(Vet vet) {
        return VetJpaEntity.builder()
                .vetId(vet.getVetId())
                .vetName(vet.getVetName())
                .vetImage(vet.getVetImage())
                .address(vet.getAddress())
                .vetIntroduction(vet.getVetIntroduction())
                .build();
    }

    public Vet toModel() {
        return Vet.builder()
                .vetId(vetId)
                .vetName(vetName)
                .vetImage(vetImage)
                .address(address)
                .vetIntroduction(vetIntroduction)
                .build();
    }
}
