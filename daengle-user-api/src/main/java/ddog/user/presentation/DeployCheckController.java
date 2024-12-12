package ddog.user.presentation;

import ddog.auth.config.jwt.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class DeployCheckController {
    private String deploymentTime;
    private final JwtTokenProvider jwtTokenProvider;

    // 애플리케이션 시작 시 배포 시간을 기록
    @PostConstruct
    public void init() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        deploymentTime = now.format(formatter);
    }

    @GetMapping("/test")
    public String test() {
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = now.format(formatter);
        return "Hello Daengle World - MULTI MODULE !!!! USER API !" +
                " Made at: " + deploymentTime + "   CI/CD SUCCESS";
    }

    @PostMapping("/test/accessToken")
    public String getAccessToken(@RequestBody TokenReq tokenReq, HttpServletResponse response) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + tokenReq.getRole().toString()));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(tokenReq.getEmail() + "," + tokenReq.getAccountId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication, response);
    }
}
