package ddog.daengleserver.global.auth.dto;

import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.infrastructure.jpa.AccountJpaEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenAccountInfoDto {

    private Long id;
    private String email;
    private String nickname;
    private Provider provider;
    private Role role;

    @Builder
    public TokenAccountInfoDto(AccountJpaEntity accountJpaEntity) {
        this.id = accountJpaEntity.getId();
        this.email = accountJpaEntity.getEmail();
        this.nickname = accountJpaEntity.getNickname();
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
