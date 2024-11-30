package ddog.auth.dto;

import ddog.domain.account.Provider;
import ddog.domain.account.Role;
import ddog.persistence.jpa.entity.AccountJpaEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenAccountInfoDto {

    private Long id;
    private String email;
    private Provider provider;
    private Role role;

    @Builder
    public TokenAccountInfoDto(AccountJpaEntity accountJpaEntity) {
        this.id = accountJpaEntity.getAccountId();
        this.email = accountJpaEntity.getEmail();
        this.provider = accountJpaEntity.getProvider();
        this.role = accountJpaEntity.getRole();
    }

    @Getter
    @Builder
    public static class TokenInfo {
        private String email;
        private String role;
    }
}
