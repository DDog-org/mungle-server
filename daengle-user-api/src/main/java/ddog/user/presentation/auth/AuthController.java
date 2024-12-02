package ddog.user.presentation.auth;

import ddog.auth.dto.KakaoAccessTokenDto;
import ddog.auth.dto.RefreshTokenDto;
import ddog.user.application.auth.AuthService;
import ddog.user.application.exception.common.CommonResponseEntity;
import ddog.user.presentation.auth.dto.LoginResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.user.application.exception.common.CommonResponseEntity.success;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daengle")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    public CommonResponseEntity<LoginResult> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto, HttpServletResponse response) {
        return success(authService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken(), response));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<LoginResult> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        return success(authService.reGenerateAccessToken(refreshTokenDto.getRefreshToken(), response));
    }
}
