package ddog.mungleserver.global.auth.dto;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.enums.Role;
import ddog.mungleserver.infrastructure.jpa.Customer;
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
    public TokenAccountInfoDto(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.nickname = customer.getNickname();
        this.provider = customer.getProvider();
        this.role = customer.getRole();
    }

    @Getter
    @Builder
    public static class TokenInfo {
        private String email;
        private String provider;
    }
}
