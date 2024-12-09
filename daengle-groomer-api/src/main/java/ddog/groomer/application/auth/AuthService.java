package ddog.groomer.application.auth;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.AccessTokenInfo;
import ddog.auth.dto.TokenAccountInfoDto;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.account.Status;
import ddog.groomer.presentation.auth.dto.LoginResult;
import ddog.persistence.mysql.port.AccountPersist;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoSocialService kakaoSocialService;
    private final AccountPersist accountPersist;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResult kakaoOAuthLogin(String kakaoAccessToken, HttpServletResponse response) {
        /* kakaoAccessToken 정보를 가지고 유저의 이메일 정보를 가져온다. */
        String email = kakaoSocialService.getKakaoEmail(kakaoAccessToken);
        Role role = Role.GROOMER;

        Account account = accountPersist.findAccountByEmailAndRole(email, role)
                .orElse(null);

        if (account == null) {
            return LoginResult.builder()
                    .isOnboarding(true)
                    .isPending(false)
                    .email(email)
                    .build();
        }

        if (account.getStatus().equals(Status.PENDING)) {
            return LoginResult.builder()
                    .isOnboarding(false)
                    .isPending(true)
                    .build();
        }
        Authentication authentication = getAuthentication(email, role);
        String accessToken = jwtTokenProvider.generateToken(authentication, response);
        return LoginResult.builder()
                .isOnboarding(false)
                .isPending(false)
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    private Authentication getAuthentication(String email, Role role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GROOMER"));

        Long accountId = accountPersist.findAccountByEmailAndRole(email, role)
                .orElseThrow(() -> new RuntimeException("Account Not Found"))
                .getAccountId();

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public AccessTokenInfo reGenerateAccessToken(String refreshToken, HttpServletResponse response) {
        if (!jwtTokenProvider.validateToken(refreshToken.substring(7).trim())) {
            throw new AuthException(AuthExceptionType.INVALID_TOKEN);
        }

        TokenAccountInfoDto.TokenInfo tokenInfo = jwtTokenProvider.extractTokenInfoFromJwt(refreshToken);
        String email = tokenInfo.getEmail();

        Authentication authentication = getAuthentication(email, Role.GROOMER);
        String accessToken = jwtTokenProvider.generateToken(authentication, response);
        return AccessTokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }
}
