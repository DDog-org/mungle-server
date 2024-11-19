package ddog.daengleserver.infrastructure.jpa;

import ddog.daengleserver.domain.Customer;
import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.global.auth.config.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Customer")
public class CustomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public static CustomerJpaEntity from(Customer customer) {
        CustomerJpaEntity customerJpaEntity = new CustomerJpaEntity();
        customerJpaEntity.id = customer.getId();
        customerJpaEntity.email = customer.getEmail();
        customerJpaEntity.nickname = customer.getNickname();
        customerJpaEntity.provider = customer.getProvider();
        customerJpaEntity.role = customer.getRole();
        return customerJpaEntity;
    }

    public Customer toModel() {
        return Customer.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .provider(this.provider)
                .role(this.role)
                .build();
    }
}
