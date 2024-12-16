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
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "10.10.223.78:3000",
                                "https://dev-user.daengle.com", "https://dev-groomer.daengle.com", "https://dev-vet.daengle.com",
                                "https://daengle.com", "https://www.daengle.com", "https://groomer.daengle.com", "https://vet.daengle.com"
                        ));
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Authorization-refresh", "X-Api-Key"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
