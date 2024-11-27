package ddog.daengleserver.presentation;

import ddog.daengleserver.application.CustomMessageService;
import ddog.daengleserver.application.KakaoAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoPushController {
    KakaoAuthService authService;
    CustomMessageService customMessageService;

    @GetMapping("/send")
    public String serviceStart(String code) {
        if (authService.getKakaoAuthToken(code)) {
            customMessageService.sendMessage();
            return "메시지 전송 성공";
        }
        else {
            return "토큰 발급 실패";
        }
    }
}
