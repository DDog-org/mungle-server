package ddog.auth.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.RefreshTokenDto;
import ddog.auth.dto.TokenAccountInfoDto;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.enums.AuthExceptionType;
import ddog.daengleserver.application.repository.AccountRepository;
import ddog.account.enums.Role;
import ddog.auth.dto.LoginResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OAuthService {

    public static final String ROLE = "ROLE_";
    private final KakaoSocialService kakaoSocialService;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public static Role fromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new AuthException(AuthExceptionType.UNAVAILABLE_ROLE);
    }

    public LoginResult kakaoOAuthLogin(String kakaoAccessToken, String loginType, HttpServletResponse response) {
        /* kakaoAccessToken 정보를 가지고 유저의 이메일 정보를 가져온다. */
        HashMap<String, Object> kakaoUserInfo = kakaoSocialService.getKakaoUserInfo(kakaoAccessToken);

        String email = kakaoUserInfo.get("email").toString();
        Role role = fromString(loginType);

        if (!accountRepository.checkExistsAccountBy(email, role)) {
            return LoginResult.builder()
                    .isOnboarding(true)
                    .email(email)
                    .role(role)
                    .build();
        }
        return jwtTokenProvider.generateToken(getAuthentication(email, loginType), role, response);
    }

    private Authentication getAuthentication(String email, String roleString) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE + roleString));

        Role role = fromString(roleString);
        Long accountId = accountRepository.findAccountByEmailAndRole(email, role).getAccountId();

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public LoginResult reGenerateAccessToken(RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken.substring(7).trim())) {
            /* 추후에 INVALID_TOKEN 으로 변경 예정 */
            throw new RuntimeException();
        }

        TokenAccountInfoDto.TokenInfo tokenInfo = jwtTokenProvider.extractTokenInfoFromJwt(refreshToken);
        String email = tokenInfo.getEmail();

        return jwtTokenProvider.generateToken(getAuthentication(email, ROLE + refreshTokenDto.getLoginType()), Role.DAENGLE, response);
    }
}
