package ddog.auth.config.jwt;

import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /* 유효한 자격증명을 제공하지 않고 접근할 시 401 에러 */
        throw new AuthException(AuthExceptionType.INVALID_TOKEN);
    }
}
