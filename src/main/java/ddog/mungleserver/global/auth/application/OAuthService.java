package ddog.mungleserver.global.auth.application;

import ddog.mungleserver.global.auth.config.enums.Provider;
import ddog.mungleserver.global.auth.config.jwt.JwtTokenProvider;
import ddog.mungleserver.global.auth.dto.RefreshTokenDto;
import ddog.mungleserver.global.auth.dto.TokenAccountInfoDto;
import ddog.mungleserver.global.auth.dto.TokenInfoDto;
import ddog.mungleserver.global.auth.config.enums.Role;
import ddog.mungleserver.infrastructure.jpa.Customer;
import ddog.mungleserver.infrastructure.CustomerRepository;
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

    private final KakaoSocialService kakaoSocialService;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfoDto kakaoOAuthLogin(String kakaoAccessToken) {
        HashMap<String, Object> kakaoUserInfo = kakaoSocialService.getKakaoUserInfo(kakaoAccessToken);
        String email = kakaoUserInfo.get("email").toString();
        Provider provider = Provider.KAKAO;

        if (customerRepository.notExistsAccountByEmailAndProvider(email, provider)) {
            saveAccount(kakaoUserInfo, email);
        }

        Role role = Role.CUSTOMER;
        return jwtTokenProvider.generateToken(getAuthentication(email, String.valueOf(provider)), role);
    }

    private void saveAccount(HashMap<String, Object> kakaoUserInfo, String email) {
        String nickname = kakaoUserInfo.get("nickname").toString();
        Customer customer = Customer.builder()
                .provider(Provider.KAKAO)
                .email(email)
                .nickname(nickname)
                .role(Role.CUSTOMER)
                .build();
        customerRepository.save(customer);
    }

    private Authentication getAuthentication(String email, String provider) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

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

        return jwtTokenProvider.generateToken(getAuthentication(email, provider), Role.CUSTOMER);
    }
}
