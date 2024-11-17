package ddog.mungleserver.presentation.oauth;

import ddog.mungleserver.infrastructure.auth.dto.KakaoAccessTokenDto;
import ddog.mungleserver.infrastructure.auth.dto.RefreshTokenDto;
import ddog.mungleserver.infrastructure.auth.dto.ResponseDto;
import ddog.mungleserver.infrastructure.auth.dto.ResponseStatus;
import ddog.mungleserver.infrastructure.auth.dto.TokenInfoDto;
import ddog.mungleserver.infrastructure.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public ResponseDto<TokenInfoDto> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {
        return new ResponseDto(ResponseStatus.SUCCESS, "카카오 로그인 성공", oAuthService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseDto<TokenInfoDto> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        oAuthService.reGenerateAccessToken(refreshTokenDto);
        return new ResponseDto(ResponseStatus.SUCCESS, "토큰 재발급", oAuthService.reGenerateAccessToken(refreshTokenDto));
    }
}
