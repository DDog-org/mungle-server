package ddog.daengleserver.infrastructure.jpa;

import ddog.daengleserver.domain.Account;
import ddog.daengleserver.domain.Provider;
import ddog.daengleserver.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Account")
public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String email;
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public static AccountJpaEntity from(Account account) {
        return AccountJpaEntity.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .provider(account.getProvider())
                .role(account.getRole())
                .build();
    }

    public Account toModel() {
        return Account.builder()
                .accountId(this.accountId)
                .email(this.email)
                .nickname(this.nickname)
                .provider(this.provider)
                .role(this.role)
                .build();
    }
}
