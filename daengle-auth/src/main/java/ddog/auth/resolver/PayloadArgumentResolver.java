package ddog.auth.resolver;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import ddog.domain.account.Role;
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

        // TODO 회원/비회원 로직을 처리하기 위해 리팩토링 전 적어둔 로직, 추후 리팩토링 때 회원/비회원 로직 처리를 어떻게 해야할지 고민 필요
        if (token == null) {
            return new PayloadDto(null, null, null);
        }

        if (jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.parseClaims(token);
            String[] subjects = claims.getSubject().split(",");
            String email = subjects[0];
            Long accountId = Long.valueOf(subjects[1]);
            Role role = fromString(claims.get("auth", String.class).substring(5));

            return new PayloadDto(accountId, email, role);
        }

        return null;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // TODO 회원/비회원 로직을 처리하기 위해 리팩토링 전 적어둔 로직, 추후 리팩토링 때 회원/비회원 로직 처리를 어떻게 해야할지 고민 필요
        if (bearerToken == null) {
            return null;
        }

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        throw new AuthException(AuthExceptionType.UNSUPPORTED_TOKEN);
    }

    private Role fromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new RuntimeException();
    }
}