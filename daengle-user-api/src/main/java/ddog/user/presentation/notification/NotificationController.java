package ddog.user.presentation.notification;

import ddog.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public SseEmitter connectSseEmitter(@PathVariable("userId") Long userId) {
        return notificationService.connect(userId);
    }
}
