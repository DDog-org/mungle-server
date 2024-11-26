package ddog.daengleserver.presentation;

import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.usecase.SseEmitterUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class SseEmitterController {
    private final SseEmitterUsecase sseEmitterUsecase;
    private static final String INIT = "{\"message\" : \"CONNECTED\"}";


    @GetMapping(value = "/connection", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connectNotificationStream(Long userId){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterUsecase.addEmitter(userId, sseEmitter);

        try {
            sseEmitter.send(INIT);
        } catch (IOException e) {
            sseEmitterUsecase.removeEmitter(userId);
        }
        return sseEmitter;
    }

    @GetMapping("/connection-status")
    public CommonResponseEntity<String> connectionStatus(@RequestParam Long userId) {
        boolean isConnected = sseEmitterUsecase.isUserConnected(userId);
        String message = isConnected ? "User is connected" : "User is not connected";
        return success(message);
    }

    @PostMapping("/send")
    public CommonResponseEntity<String> sendNotification(@RequestBody NotificationReq notificationReq) {
        sseEmitterUsecase.sendMessageToUser(notificationReq.getUserId(), notificationReq.getMessage());
        return success("Notification sent");
    }
}
