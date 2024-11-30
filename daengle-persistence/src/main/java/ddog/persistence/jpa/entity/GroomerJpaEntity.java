package ddog.persistence.jpa.entity;

import ddog.domain.groomer.Groomer;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groomerId;
    private Long accountId;
    private int daengleMeter;
    private String groomerName;
    private String groomerImage;
    private String address;
    private String shopName;
    private String groomerIntroduction;

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .accountId(groomer.getAccountId())
                .daengleMeter(groomer.getDaengleMeter())
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
                .accountId(accountId)
                .daengleMeter(daengleMeter)
                .groomerName(groomerName)
                .groomerImage(groomerImage)
                .address(address)
                .shopName(shopName)
                .groomerIntroduction(groomerIntroduction)
                .build();
    }
}
