package ddog.mungleserver.global.auth.dto;

import lombok.Getter;

@Getter
public class RefreshTokenDto {
    private String refreshToken;
    private String loginType;
}
