package ddog.mungleserver.global.auth.dto;

import ddog.mungleserver.global.auth.config.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfoDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Role role;
}
