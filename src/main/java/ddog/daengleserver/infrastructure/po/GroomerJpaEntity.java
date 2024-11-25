package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Groomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Groomers")
public class GroomerJpaEntity {

    @Id
    private Long groomerId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpaEntity account;
    private String groomerName;
    private String groomerImage;
    private String address;
    private String shopName;
    private String groomerIntroduction;

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .groomerName(groomer.getGroomerName())
                .groomerImage(groomer.getGroomerName())
                .address(groomer.getAddress())
                .shopName(groomer.getShopName())
                .groomerIntroduction(groomer.getGroomerIntroduction())
                .build();
    }

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(groomerId)
                .groomerName(groomerName)
                .groomerImage(groomerImage)
                .address(address)
                .shopName(shopName)
                .groomerIntroduction(groomerIntroduction)
                .build();
    }
}
