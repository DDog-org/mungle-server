package ddog.daengleserver.infrastructure.jpa;

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

    public Groomer toModel() {
        return Groomer.builder()
                .groomerId(this.groomerId)
                .groomerName(this.groomerName)
                .groomerImage(this.groomerImage)
                .address(this.address)
                .build();
    }

    public static GroomerJpaEntity from(Groomer groomer) {
        return GroomerJpaEntity.builder()
                .groomerId(groomer.getGroomerId())
                .groomerName(groomer.getGroomerName())
                .groomerImage(groomer.getGroomerName())
                .address(groomer.getAddress())
                .build();
    }
}
