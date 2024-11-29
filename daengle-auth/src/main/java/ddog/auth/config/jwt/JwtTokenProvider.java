package ddog.auth.config.jwt;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.account.enums.Role;
import ddog.auth.dto.LoginResult;
import ddog.auth.dto.TokenAccountInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final AccountRepository accountRepository;
    /* accessToken 만료 시간 */
    private final int ACCESSTOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 3;    // 3일
    /* refreshToken 만료 시간*/
    private final int REFRESHTOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 14;   // 14일

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secret, AccountRepository accountRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accountRepository = accountRepository;
    }

    public LoginResult generateToken(Authentication authentication, Role role, HttpServletResponse response) {
        String accessToken = createToken(authentication, ACCESSTOKEN_EXPIRATION_TIME);
        String refreshToken = createToken(authentication, REFRESHTOKEN_EXPIRATION_TIME);
        response.setHeader("Set-Cookie", ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .maxAge(REFRESHTOKEN_EXPIRATION_TIME)
                .path("/")
                .sameSite("None")
                .build().toString());

        return LoginResult.builder()
                .isOnboarding(false)
                .role(role)
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    private String createToken(Authentication authentication, int expirationTime) {
        Date tokenExpiration = new Date(getNowTime() + expirationTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", getAuthorities(authentication))
                .setExpiration(tokenExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            /* 추후 에러 처리 작업 들어가면 RuntimeException 대신 커스텀 예외로 수정해줘야 함. */
            throw new RuntimeException();
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(accessToken, null, authorities);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            /* 추후 에러 처리 작업 들어가면 RuntimeException 대신 커스텀 예외로 수정해줘야 함. */
            throw new RuntimeException(e);
        }
    }

    private long getNowTime() {
        return new Date().getTime();
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            /* 추후 INVALID_TOKEN 으로 변경 예정*/
            throw new RuntimeException();
        } catch (ExpiredJwtException e) {
            /* 추후 EXPIRED_TOKEN 으로 변경 예정 */
            throw new RuntimeException();
        } catch (UnsupportedJwtException e) {
            /* 추후 UNSUPPORTED_TOKEN 으로 변경 예정*/
            throw new RuntimeException();
        } catch (IllegalArgumentException e) {
            /* 추후 UNKNOWN_ERROR 으로 변경 예정 */
            throw new RuntimeException();
        }
    }

    public TokenAccountInfoDto.TokenInfo extractTokenInfoFromJwt(String token) {
        if (token.startsWith("Bearer ")) {
            String resolvedToken = token.substring(7).trim();
            Claims claims = parseClaims(resolvedToken);
            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            return TokenAccountInfoDto.TokenInfo.builder()
                    .email(email)
                    .role(role)
                    .build();
        }

        return null;
    }
}
