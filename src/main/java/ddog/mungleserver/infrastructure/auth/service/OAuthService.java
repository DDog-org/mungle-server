package ddog.mungleserver.infrastructure.auth.service;

import ddog.mungleserver.infrastructure.auth.config.enums.Provider;
import ddog.mungleserver.infrastructure.auth.config.enums.Role;
import ddog.mungleserver.infrastructure.auth.config.jwt.JwtTokenProvider;
import ddog.mungleserver.infrastructure.auth.dto.TokenInfoDto;
import ddog.mungleserver.jpa.customer.Customer;
import ddog.mungleserver.persistence.Customer.CustomerRepository;
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
        Customer customer = Customer.builder()
                .provider(Provider.KAKAO)
                .email(email)
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
}
