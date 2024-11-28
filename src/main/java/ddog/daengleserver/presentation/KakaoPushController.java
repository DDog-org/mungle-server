package ddog.daengleserver.presentation;

import ddog.daengleserver.application.KakaoPushService;
import ddog.daengleserver.presentation.dto.request.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoPushController {
    private final KakaoPushService kakaoPushService;

    public KakaoPushController(KakaoPushService kakaoPushService) {
        this.kakaoPushService = kakaoPushService;
    }

    // 카카오 알림톡 메시지 전송
    @PostMapping("/api/kakao-send")
    public ResponseEntity<String> sendNotification(@RequestBody MessageDto requestDto) {
        // MessageDto를 이용해 알림톡 메시지 전송
        boolean isSuccess = kakaoPushService.sendNotification(requestDto.getTargetUuid(), requestDto.getMessage());

        if (isSuccess) {
            return ResponseEntity.ok("알림톡 전송 성공");
        } else {
            return ResponseEntity.status(500).body("알림톡 전송 실패");
        }
    }
}
