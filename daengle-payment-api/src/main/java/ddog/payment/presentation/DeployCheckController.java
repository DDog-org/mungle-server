package ddog.payment.presentation;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/payment")
public class DeployCheckController {
    private String deploymentTime;

    // 애플리케이션 시작 시 배포 시간 기록
    @PostConstruct
    public void init() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        deploymentTime = now.format(formatter);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello Daengle World - MULTI MODULE !!!! PAYMENT API !" +
                " Made at: " + deploymentTime + "   CI/CD SUCCESS";
    }
}
