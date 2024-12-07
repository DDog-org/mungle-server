package ddog.vet.presentation.notification;

import ddog.notification.application.adapter.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vet/notify")
public class NotificationController {

    private final SseEmitterManager sseEmitterManager;

    @GetMapping("/{userId}")
    public SseEmitter connectSseEmitter(@PathVariable("userId") Long userId) {
        return sseEmitterManager.toConnectEmitter(userId);
    }
}
