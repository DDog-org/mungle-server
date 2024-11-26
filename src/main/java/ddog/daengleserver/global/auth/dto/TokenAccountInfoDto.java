package ddog.daengleserver.global.auth.dto;

import ddog.daengleserver.domain.account.enums.Provider;
import ddog.daengleserver.domain.account.enums.Role;
import ddog.daengleserver.infrastructure.po.AccountJpaEntity;
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
