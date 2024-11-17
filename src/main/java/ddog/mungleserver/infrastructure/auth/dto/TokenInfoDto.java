package ddog.mungleserver.infrastructure.auth.dto;

import ddog.mungleserver.infrastructure.auth.config.enums.Role;
import lombok.Builder;

@Builder
public class TokenInfoDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Role role;
}
