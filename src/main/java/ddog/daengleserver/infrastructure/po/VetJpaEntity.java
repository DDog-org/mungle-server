package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.account.Vet;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vetId;
    private Long accountId;
    private int daengleMeter;
    private String vetName;
    private String vetImage;
    private String address;
    private String vetIntroduction;
    private String phoneNumber;

    public static VetJpaEntity from(Vet vet) {
        return VetJpaEntity.builder()
                .vetId(vet.getVetId())
                .accountId(vet.getAccountId())
                .daengleMeter(vet.getDaengleMeter())
                .vetName(vet.getVetName())
                .vetImage(vet.getVetImage())
                .address(vet.getAddress())
                .vetIntroduction(vet.getVetIntroduction())
                .phoneNumber(vet.getPhoneNumber())
                .build();
    }

    public Vet toModel() {
        return Vet.builder()
                .vetId(vetId)
                .accountId(accountId)
                .daengleMeter(daengleMeter)
                .vetName(vetName)
                .vetImage(vetImage)
                .address(address)
                .vetIntroduction(vetIntroduction)
                .phoneNumber(phoneNumber)
                .build();
    }
}
