package ddog.auth.config.jwt;

import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

/* JWT 인증 필터 클래스로 HTTP 요청에서 JWT 토큰을 추출하고 검증하여 사용자 인증 정보를 설정한다.
* Spring Security의 필터 체인에 추가된다. */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /* 필터 제외 URL 리스트 */
    private final List<String> excludeUrls = List.of(
            "/api/user/test", "/api/groomer/test", "/api/vet/test", "/api/payment/test",
            "/api/user/kakao", "/api/groomer/kakao", "/api/vet/kakao", "/api/user/refresh-token", "/api/groomer/refresh-token", "/api/vet/refresh-token",
            "/api/user/available-nickname", "/api/user/breed/list", "/api/user/join-with-pet", "/api/user/join-without-pet",
            "/api/groomer/join", "/api/vet/join"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        /* TODO 나중에 꼭 빼기 */
        if (true) {
            chain.doFilter(request, response);
            return;
        }

        if (excludeUrls.contains(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String token = resolveToken((HttpServletRequest) request);
        try {
            if (jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionType.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new AuthException(AuthExceptionType.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            throw new AuthException(AuthExceptionType.UNKNOWN_TOKEN);
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new AuthException(AuthExceptionType.MISSING_TOKEN);
        }
        if (token.isEmpty()) {
            throw new AuthException(AuthExceptionType.EMPTY_TOKEN);
        }

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
