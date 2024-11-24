package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.infrastructure.po.AccountJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Groomer")
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

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(groomerId)
                .groomerName(groomerName)
                .groomerImage(groomerImage)
                .address(address)
                .shopName(shopName)
                .build();
    }

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .groomerName(groomer.getGroomerName())
                .groomerImage(groomer.getGroomerName())
                .address(groomer.getAddress())
                .shopName(groomer.getShopName())
                .build();
    }
}
