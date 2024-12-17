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


/* 컨트롤러 메서드의 파라미텅에 사용자 인증 정보인 Payload를 주입하는 Resolver 클래스로
 * JWT 토큰을 파싱하고 사용자의 Account Id, 이메일, 권한을 추출하여 PayloadDto로 반환한다. */
@Slf4j
@Component
@RequiredArgsConstructor
public class PayloadArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = resolveToken(request);

        // TODO 회원/비회원 로직을 처리하기 위해 리팩토링 전 적어둔 로직, 추후 리팩토링 때 회원/비회원 로직 처리를 어떻게 해야할지 고민 필요
        /* 토큰이 없는 경우 비회원 처리를 위해 비어있는 PayloadDto 반환 */
        if (token == null) {
            return new PayloadDto(null, null, null);
        }

        /* 유효한 토큰인 경우 파싱을 통해 PayloadDto 반환 */
        if (jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.parseClaims(token);
            String[] subjects = claims.getSubject().split(",");
            String email = subjects[0];
            Long accountId = Long.valueOf(subjects[1]);
            Role role = fromString(claims.get("auth", String.class).substring(5));

            return new PayloadDto(accountId, email, role);
        }

        /* 유효하지 않은 토큰인 경우 null 반환 */
        return null;
    }

    /* 이 ArgumentResolver가 특정 파라미터를 지원하는지 여부 확인
     *
     * @param parameter 컨트롤러 메서드의 파라미터
     * @return PayloadDto 타입인 경우 true 반환
     *  */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 컨트롤러 메서드 매개변수가 Payload 타입인 경우 처리
        return parameter.getParameterType().equals(PayloadDto.class);
    }

    /* 요청 헤더에서 JWT 토큰을 추출한다. */
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

    /* 문자열로부터 Role enum 객체를 반환한다. */
    private Role fromString(String roleString) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new RuntimeException();
    }
}