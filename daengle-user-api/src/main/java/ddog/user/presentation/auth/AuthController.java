package ddog.user.presentation.auth;

import ddog.auth.dto.AccessTokenInfo;
import ddog.auth.dto.KakaoAccessTokenDto;
import ddog.auth.dto.PayloadDto;
import ddog.auth.dto.RefreshTokenDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.auth.AuthService;
import ddog.user.presentation.auth.dto.LoginResult;
import ddog.user.presentation.auth.dto.ValidateResp;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    public CommonResponseEntity<LoginResult> kakaoLogin(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto, HttpServletResponse response) {
        return success(authService.kakaoOAuthLogin(kakaoAccessTokenDto.getKakaoAccessToken(), response));
    }

    @PostMapping("/refresh-token")
    public CommonResponseEntity<AccessTokenInfo> reGenerateAccessToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        return success(authService.reGenerateAccessToken(refreshTokenDto.getRefreshToken(), response));
    }

    @GetMapping("/validate")
    public CommonResponseEntity<ValidateResp> validateMember(PayloadDto payloadDto) {
        return success(authService.validateMember(payloadDto.getAccountId()));
    }
}
