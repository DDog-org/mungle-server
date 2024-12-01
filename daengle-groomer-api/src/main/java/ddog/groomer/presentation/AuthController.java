package ddog.groomer.presentation;

import ddog.auth.dto.KakaoAccessTokenDto;
import ddog.auth.dto.LoginResult;
import ddog.auth.dto.RefreshTokenDto;
import ddog.groomer.application.auth.AuthService;
import ddog.groomer.application.exception.common.CommonResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.groomer.application.exception.common.CommonResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    public CommonResponseEntity<LoginResult> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto, HttpServletResponse response) {
        return success(authService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken(), kakaoAccessTokenDto.getLoginType(), response));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<LoginResult> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        return success(authService.reGenerateAccessToken(refreshTokenDto, response));
    }
}