package ddog.mungleserver.global.auth.application;

import ddog.mungleserver.application.repository.CustomerRepository;
import ddog.mungleserver.domain.Customer;
import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.jwt.JwtTokenProvider;
import ddog.mungleserver.global.auth.dto.RefreshTokenDto;
import ddog.mungleserver.global.auth.dto.TokenAccountInfoDto;
import ddog.mungleserver.global.auth.dto.TokenInfoDto;
import ddog.mungleserver.global.auth.config.enums.Role;
import ddog.mungleserver.global.auth.exception.AuthException;
import ddog.mungleserver.global.auth.exception.enums.AuthExceptionType;
import ddog.mungleserver.infrastructure.jpa.CustomerJpaEntity;
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
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OAuthService {

    public static final String ROLE = "ROLE_";
    private final KakaoSocialService kakaoSocialService;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfoDto kakaoOAuthLogin(String kakaoAccessToken, String loginType) {
        HashMap<String, Object> kakaoUserInfo = kakaoSocialService.getKakaoUserInfo(kakaoAccessToken);
        String email = kakaoUserInfo.get("email").toString();
        Provider provider = Provider.KAKAO;

        Role role = fromString(loginType);
        if (!customerRepository.checkExistsAccountBy(email, provider)) {
            /* 추후에 만약 회원이 없다면 회원가입 페이지로 보낼 수 있도록 로직 변경하며,
            * saveAccount() 는 회원가입에서 등록했을 때 동작하도록 로직 변경해줘야 함. */
            saveAccount(kakaoUserInfo, email, role);
        }

        return jwtTokenProvider.generateToken(getAuthentication(email, String.valueOf(provider), ROLE + loginType), role);
    }

    private void saveAccount(HashMap<String, Object> kakaoUserInfo, String email, Role role) {
        String nickname = kakaoUserInfo.get("nickname").toString();
        Customer customer = Customer.builder()
                .provider(Provider.KAKAO)
                .email(email)
                .nickname(nickname)
                .role(role)
                .build();
        customerRepository.save(customer);
    }

    private Authentication getAuthentication(String email, String provider, String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + provider, null, authorities);
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
        String provider = tokenInfo.getProvider();

        return jwtTokenProvider.generateToken(getAuthentication(email, provider, ROLE + refreshTokenDto.getLoginType()), Role.CUSTOMER);
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
