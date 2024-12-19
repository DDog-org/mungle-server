package ddog.payment.presentation;

import ddog.domain.message.port.MessageSend;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class DeployCheckController {
    private String deploymentTime;

    private final MessageSend messageSend;

    // 애플리케이션 시작 시 배포 시간 기록
    @PostConstruct
    public void init() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        deploymentTime = now.format(formatter);
    }

    @GetMapping("/test")
    public String test() {

        messageSend.send("Hello Message Queue World I'm here!");

        return "Hello Daengle World - DIFF -MULTI MODULE !!!! PAYMENT API  2트!" +
                " Made at: " + deploymentTime + "   CI/CD SUCCESS";
    }
}
