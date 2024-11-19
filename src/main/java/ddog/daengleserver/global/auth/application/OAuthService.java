package ddog.daengleserver.global.auth.application;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.domain.Account;
import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.global.auth.config.jwt.JwtTokenProvider;
import ddog.daengleserver.global.auth.dto.RefreshTokenDto;
import ddog.daengleserver.global.auth.dto.TokenAccountInfoDto;
import ddog.daengleserver.global.auth.dto.TokenInfoDto;
import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.global.auth.exception.AuthException;
import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
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

    public TokenInfoDto kakaoOAuthLogin(String kakaoAccessToken, String loginType) {
        /* kakaoAccessToken 정보를 가지고 유저의 닉네임, 이메일 정보를 가져온다. */
        HashMap<String, Object> kakaoUserInfo = kakaoSocialService.getKakaoUserInfo(kakaoAccessToken);

        String email = kakaoUserInfo.get("email").toString();

        Role role = fromString(loginType);
        if (!accountRepository.checkExistsAccountBy(email, role)) {
            /* 추후에 만약 회원이 없다면 회원가입 페이지로 보낼 수 있도록 로직 변경하며,
            * saveAccount() 는 회원가입에서 등록했을 때 동작하도록 로직 변경해줘야 함. */
            saveAccount(kakaoUserInfo, email, role);
        }

        return jwtTokenProvider.generateToken(getAuthentication(email,ROLE + loginType), role);
    }

    private void saveAccount(HashMap<String, Object> kakaoUserInfo, String email, Role role) {
        String nickname = kakaoUserInfo.get("nickname").toString();
        Account account = Account.builder()
                .provider(Provider.KAKAO)
                .email(email)
                .nickname(nickname)
                .role(role)
                .build();
        accountRepository.save(account);
    }

    private Authentication getAuthentication(String email, String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + role, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public TokenInfoDto reGenerateAccessToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken.substring(7).trim())) {
            /* 추후에 INVALID_TOKEN 으로 변경 예정 */
            throw new RuntimeException();
        }

        TokenAccountInfoDto.TokenInfo tokenInfo = jwtTokenProvider.extractTokenInfoFromJwt(refreshToken);
        String email = tokenInfo.getEmail();

        return jwtTokenProvider.generateToken(getAuthentication(email,ROLE + refreshTokenDto.getLoginType()), Role.CUSTOMER);
    }

    public static Role fromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new AuthException(AuthExceptionType.UNAVAILABLE_ROLE);
    }
}
