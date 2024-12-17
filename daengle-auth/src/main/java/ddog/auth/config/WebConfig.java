package ddog.auth.config;

import ddog.auth.resolver.PayloadArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/* WebMVC 설정 클래스로 커스텀 핸들러 메서드 ArgumentResolver를 추가해 컨트롤러 메서드에서 사용할 수 있도록 설정한다. */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /* PayloadArgumentResolver는 컨트롤러의 메서드 인자로 전달되는 커스텀 객체를 처리한다. */
    private final PayloadArgumentResolver payloadArgumentResolver;

    /* 커스텀 ArgumentResolver를 Spring MVC에 등록한다.
    * @param resolvers 기본 및 커스텀 ArgumentResolver 목록 */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(payloadArgumentResolver);
    }
}