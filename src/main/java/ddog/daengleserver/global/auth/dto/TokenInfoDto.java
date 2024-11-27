package ddog.daengleserver.global.auth.dto;

import ddog.daengleserver.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfoDto {

    private final String grantType;
    private final String accessToken;
    private final Role role;
}
