package ddog.daengleserver.global.auth.resolver;

import ddog.daengleserver.global.auth.config.enums.Role;
import ddog.daengleserver.global.auth.config.jwt.JwtTokenProvider;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.auth.exception.AuthException;
import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayloadArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 컨트롤러 메서드 매개변수가 Payload 타입인 경우 처리
        return parameter.getParameterType().equals(PayloadDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = resolveToken(request);

        if (jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.parseClaims(token);
            String email = claims.getSubject();
            Role role = fromString(claims.get("auth", String.class).substring(5));

            return new PayloadDto(email, role);
        }

        return null;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        /* 추후 예외 변경 필요 */
        throw new AuthException(AuthExceptionType.UNAVAILABLE_TOKEN);
    }

    private Role fromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new AuthException(AuthExceptionType.UNAVAILABLE_ROLE);
    }
}