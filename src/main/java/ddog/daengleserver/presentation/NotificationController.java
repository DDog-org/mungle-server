package ddog.daengleserver.presentation;

import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.usecase.SseEmitterUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final SseEmitterUsecase sseEmitterUsecase;
    private static final String INIT = "{\"message\" : \"CONNECTED\"}";

    @GetMapping(value = "/connection", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connectNotificationStream(@RequestParam(value = "userId", required = true) Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterUsecase.addEmitter(userId, sseEmitter);

        try {
            sseEmitter.send(INIT);
        } catch (IOException e) {
            sseEmitterUsecase.removeEmitter(userId);
        }
        return sseEmitter;
    }

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationReq notificationReq) {
        if (sseEmitterUsecase.isUserConnected(notificationReq.getUserId())) {
            sseEmitterUsecase.sendMessageToUser(notificationReq.getUserId(), notificationReq.getMessage());
        }
        else {
            sseEmitterUsecase.saveNotificationToDb(notificationReq);
        }
    }
}
