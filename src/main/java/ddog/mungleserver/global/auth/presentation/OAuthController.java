package ddog.mungleserver.global.auth.presentation;

import ddog.mungleserver.global.auth.application.OAuthService;
import ddog.mungleserver.global.auth.dto.KakaoAccessTokenDto;
import ddog.mungleserver.global.auth.dto.RefreshTokenDto;
import ddog.mungleserver.global.auth.dto.TokenInfoDto;
import ddog.mungleserver.global.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.mungleserver.global.common.CommonResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public CommonResponseEntity<TokenInfoDto> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {
        return success(oAuthService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken()));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<TokenInfoDto> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        oAuthService.reGenerateAccessToken(refreshTokenDto);
        return success(oAuthService.reGenerateAccessToken(refreshTokenDto));
    }
}
