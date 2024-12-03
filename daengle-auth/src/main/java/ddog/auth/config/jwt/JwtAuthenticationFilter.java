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

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    /* 필터 제외 URL 리스트 */
    private final List<String> excludeUrls = List.of(
            "/api/oauth/kakao", "/api/oauth/refresh-token", "/test"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        /* TODO 당분간 토큰 검증을 빼기 위해 임시로 넣어놓음. 나중에 꼭 빼야됨 !! */
/*        if (true) {
        /* 당분간 토큰 검증을 빼기 위해 임시로 넣어놓음. 나중에 꼭 빼야됨 !! */
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
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
