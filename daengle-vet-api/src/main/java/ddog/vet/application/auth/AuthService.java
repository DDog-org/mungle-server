package ddog.vet.application.auth;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.AccessTokenInfo;
import ddog.auth.dto.TokenAccountInfoDto;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.account.Status;
import ddog.domain.account.port.AccountPersist;
import ddog.vet.application.exception.account.AccountException;
import ddog.vet.application.exception.account.AccountExceptionType;
import ddog.vet.presentation.auth.dto.LoginResult;
import ddog.vet.presentation.auth.dto.ValidateResp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
        Role role = Role.VET;

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
        authorities.add(new SimpleGrantedAuthority("ROLE_VET"));

        Long accountId = accountPersist.findAccountByEmailAndRole(email, role)
                .orElseThrow(() -> new AccountException(AccountExceptionType.ACCOUNT_NOT_FOUND))
                .getAccountId();

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public AccessTokenInfo reGenerateAccessToken(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 refreshToken 추출
        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken == null) {
            throw new AuthException(AuthExceptionType.MISSING_TOKEN);
        }
        if (refreshToken.isEmpty()) {
            throw new AuthException(AuthExceptionType.EMPTY_TOKEN);
        }
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AuthException(AuthExceptionType.INVALID_TOKEN);
        }

        TokenAccountInfoDto.TokenInfo tokenInfo = jwtTokenProvider.extractTokenInfoFromJwt(refreshToken);
        String email = tokenInfo.getEmail();

        Authentication authentication = getAuthentication(email, Role.VET);
        String accessToken = jwtTokenProvider.generateToken(authentication, response);
        return AccessTokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public ValidateResp validateMember(Long accountId) {
        boolean isValidateMember = accountId != null;

        return ValidateResp.builder()
                .IsValidateMember(isValidateMember)
                .build();
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new AuthException(AuthExceptionType.MISSING_TOKEN); // 쿠키가 없으면 예외 처리
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }

        throw new AuthException(AuthExceptionType.MISSING_COOKIE_TOKEN);
    }
}
