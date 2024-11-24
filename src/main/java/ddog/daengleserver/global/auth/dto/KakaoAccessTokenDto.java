package ddog.daengleserver.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccessTokenDto {
    private String kakaoAccessToken;
    private String loginType;
}
