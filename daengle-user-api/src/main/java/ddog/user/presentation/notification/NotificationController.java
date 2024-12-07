package ddog.user.presentation.notification;

import ddog.notification.application.adapter.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/notify")
public class NotificationController {

    private final SseEmitterManager sseEmitterManager;

    @GetMapping("/{userId}")
    public SseEmitter connectSseEmitter(@PathVariable("userId") Long userId) {
        return sseEmitterManager.toConnectEmitter(userId);
    }
}
