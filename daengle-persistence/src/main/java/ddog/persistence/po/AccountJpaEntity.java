package ddog.persistence.po;

import ddog.daengleserver.domain.account.Account;
import ddog.account.enums.Provider;
import ddog.account.enums.Role;
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

    public static AccountJpaEntity from(Account account) {
        return AccountJpaEntity.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .provider(account.getProvider())
                .role(account.getRole())
                .build();
    }

    public Account toModel() {
        return Account.builder()
                .accountId(accountId)
                .email(email)
                .provider(provider)
                .role(role)
                .build();
    }
}
