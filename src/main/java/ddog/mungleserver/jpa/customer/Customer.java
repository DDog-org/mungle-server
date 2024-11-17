package ddog.mungleserver.jpa.customer;

import ddog.mungleserver.infrastructure.auth.config.enums.Provider;
import ddog.mungleserver.infrastructure.auth.config.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;


    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
