package ddog.persistence.mysql.jpa.entity;

import ddog.domain.account.Account;
import ddog.domain.account.Provider;
import ddog.domain.account.Role;
import ddog.domain.account.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Accounts")
public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public static AccountJpaEntity from(Account account) {
        return AccountJpaEntity.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .provider(account.getProvider())
                .role(account.getRole())
                .status(account.getStatus())
                .build();
    }

    public Account toModel() {
        return Account.builder()
                .accountId(accountId)
                .email(email)
                .provider(provider)
                .role(role)
                .status(status)
                .build();
    }
}
