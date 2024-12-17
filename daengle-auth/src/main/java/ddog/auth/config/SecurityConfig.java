package ddog.auth.config;

import ddog.auth.config.jwt.JwtAccessDeniedHandler;
import ddog.auth.config.jwt.JwtAuthenticationEntryPoint;
import ddog.auth.config.jwt.JwtAuthenticationFilter;
import ddog.auth.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /* CORS 설정 */
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        /* 허용할 도메인 목록 */
                        config.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://10.10.223.78:3000",
                                "https://dev-user.daengle.com", "https://dev-groomer.daengle.com", "https://dev-vet.daengle.com",
                                "https://daengle.com", "https://www.daengle.com", "https://groomer.daengle.com", "https://vet.daengle.com", "http://10.10.222.203:3000"
                        ));
                        /* 허용할 HTTP 메서드 */
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                        /* 쿠키와 같은 자격 증명을 허용한다. */
                        config.setAllowCredentials(true);
                        /* 허용할 헤더 */
                        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Authorization-refresh", "X-Api-Key"));
                        /* Pre-flight 요청 캐싱 시간 (1시간) */
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                /* CSRF 보호를 비활성화한다. (JWT 기반 인증이기 때문에 필요 없다) */
                .csrf(AbstractHttpConfigurer::disable)
                /* 예접근 거부 및 인증 실패 처리에 대한 예외 처리를 설정한다. */
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                /* STATELESS로 설정하여 세션을 사용하지 않는다. */
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /* URL 별 접근 권한을 설정한다. */
                .authorizeHttpRequests(request -> request
                        // 임시로 모든 API 에 대해 통과, 각 모듈 테스트 컨트롤러 API 에 대해 통과, 임시 payment 및 notification 관련 API 통과
                        .requestMatchers("/**", "/api/*/test/**", "/api/payment/**", "/notify/**", "/api/chat/**", "/chat/**").permitAll()
                        // Kakao 소셜 로그인을 위한 URL 허용
                        .requestMatchers("/api/*/kakao", "/api/*/refresh-token").permitAll()
                        // 온보딩을 위한 URL 허용
                        .requestMatchers("/api/user/available-nickname", "/api/user/breed/list", "/api/user/join-with-pet", "/api/user/join-without-pet",
                                "/api/groomer/join", "/api/vet/join").permitAll()
                        .requestMatchers("/api/user/**").hasRole("DAENGLE")
                        .requestMatchers("/api/groomer/**").hasRole("GROOMER")
                        .requestMatchers("/api/vet/**").hasRole("VET")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                )
                /* JWT 인증 필터 추가 : UsernamePasswordAuthenticationFilter 이전에 실행한다. */
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* Spring Security 에서 인증을 관리하는 객체로 빈 생성을 해준다. */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
