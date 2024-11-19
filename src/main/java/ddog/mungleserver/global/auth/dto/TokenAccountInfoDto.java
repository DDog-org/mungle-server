package ddog.mungleserver.global.auth.dto;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.enums.Role;
import ddog.mungleserver.infrastructure.jpa.CustomerJpaEntity;
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
    public TokenAccountInfoDto(CustomerJpaEntity customerJpaEntity) {
        this.id = customerJpaEntity.getId();
        this.email = customerJpaEntity.getEmail();
        this.nickname = customerJpaEntity.getNickname();
        this.provider = customerJpaEntity.getProvider();
        this.role = customerJpaEntity.getRole();
    }

    @Getter
    @Builder
    public static class TokenInfo {
        private String email;
        private String provider;
    }
}
