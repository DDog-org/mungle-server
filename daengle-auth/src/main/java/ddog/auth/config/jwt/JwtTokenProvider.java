package ddog.auth.config.jwt;

import ddog.auth.dto.TokenAccountInfoDto;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
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
    /* accessToken 만료 시간 */
    private final int ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60;    // 1시간
    /* refreshToken 만료 시간*/
    private final int REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 14;   // 14일

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Authentication authentication, HttpServletResponse response) {
        String accessToken = createToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = createToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
        response.setHeader("Set-Cookie", ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_EXPIRATION_TIME)
                .path("/")
                .sameSite("None")
                .build().toString());

        return accessToken;
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
            throw new AuthException(AuthExceptionType.MISSING_AUTH_CLAIM);
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
            throw new AuthException(AuthExceptionType.EXPIRED_TOKEN);
        }
    }

    private long getNowTime() {
        return new Date().getTime();
    }

    public boolean validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException e) {
            throw new AuthException(AuthExceptionType.INVALID_TOKEN);
        } catch (MalformedJwtException e) {
            throw new AuthException(AuthExceptionType.INVALID_TOKEN_STRUCTURE);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionType.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new AuthException(AuthExceptionType.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new AuthException(AuthExceptionType.UNKNOWN_TOKEN);
        }
    }

    public TokenAccountInfoDto.TokenInfo extractTokenInfoFromJwt(String token) {
        Claims claims = parseClaims(token);

        String email = claims.getSubject().split(",")[0];
        String role = claims.get("auth", String.class);

        return TokenAccountInfoDto.TokenInfo.builder()
                .email(email)
                .role(role)
                .build();

    }
}
