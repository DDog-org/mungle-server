package ddog.daengleserver.global.auth.application;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.domain.Account;
import ddog.daengleserver.global.auth.config.enums.Provider;
import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.global.auth.config.jwt.JwtTokenProvider;
import ddog.daengleserver.global.auth.dto.KakaoDto;
import ddog.daengleserver.global.auth.dto.RefreshTokenDto;
import ddog.daengleserver.global.auth.dto.TokenAccountInfoDto;
import ddog.daengleserver.global.auth.dto.TokenInfoDto;
import ddog.daengleserver.global.auth.exception.AuthException;
import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
import ddog.daengleserver.global.auth.util.KakaoUtil;
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
public class OAuthService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoUtil kakaoUtil;

    public TokenInfoDto kakaoOAuthLogin(String kakaoAccessToken, HttpServletResponse httpServletResponse) {

        KakaoDto.OAuthToken oAuthToken = kakaoUtil.requestToken(kakaoAccessToken);
        KakaoDto.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        Role role = Role.CUSTOMER;
        String email = kakaoProfile.getKakao_account().getEmail();
        if (!accountRepository.checkExistsAccountBy(email, role)) {
            /* 추후에 만약 회원이 없다면 회원가입 페이지로 보낼 수 있도록 로직 변경하며,
             * saveAccount() 는 회원가입에서 등록했을 때 동작하도록 로직 변경해줘야 함. */
            saveAccount(kakaoProfile, role);
        }

        return jwtTokenProvider.generateToken(getAuthentication(email, "ROLE_CUSTOMER"), role);
    }

    private void saveAccount(KakaoDto.KakaoProfile kakaoUserInfo, Role role) {
        Account account = Account.builder()
                .provider(Provider.KAKAO)
                .email(kakaoUserInfo.getKakao_account().getEmail())
                .nickname(kakaoUserInfo.getProperties().getNickname())
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

        return jwtTokenProvider.generateToken(getAuthentication(email, "ROLE_" + refreshTokenDto.getLoginType()), Role.CUSTOMER);
    }

    public String kakaoLoginPage() {
        return kakaoUtil.createURL();
    }
}
