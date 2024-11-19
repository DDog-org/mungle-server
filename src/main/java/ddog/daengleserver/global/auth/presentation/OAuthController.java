package ddog.daengleserver.global.auth.presentation;

import ddog.daengleserver.global.auth.application.OAuthService;
import ddog.daengleserver.global.auth.dto.KakaoAccessTokenDto;
import ddog.daengleserver.global.auth.dto.RefreshTokenDto;
import ddog.daengleserver.global.auth.dto.TokenInfoDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public CommonResponseEntity<TokenInfoDto> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {
        return success(oAuthService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken(), kakaoAccessTokenDto.getLoginType()));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<TokenInfoDto> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        oAuthService.reGenerateAccessToken(refreshTokenDto);
        return success(oAuthService.reGenerateAccessToken(refreshTokenDto));
    }
}
