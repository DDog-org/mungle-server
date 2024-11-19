package ddog.daengleserver.global.auth.presentation;

import ddog.daengleserver.global.auth.application.OAuthService;
import ddog.daengleserver.global.auth.dto.KakaoAccessTokenDto;
import ddog.daengleserver.global.auth.dto.RefreshTokenDto;
import ddog.daengleserver.global.auth.dto.TokenInfoDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/kakao/login")
    public ResponseEntity<Void> kakaoLoginPage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(oAuthService.kakaoLoginPage()))
                .build();
    }

    @GetMapping("/kakao")
    public CommonResponseEntity<TokenInfoDto> kakaoLogin(@RequestParam("code") String code, HttpServletResponse httpServletResponse) {
        return success(oAuthService.kakaoOAuthLogin(code, httpServletResponse));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<TokenInfoDto> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        oAuthService.reGenerateAccessToken(refreshTokenDto);
        return success(oAuthService.reGenerateAccessToken(refreshTokenDto));
    }
}
