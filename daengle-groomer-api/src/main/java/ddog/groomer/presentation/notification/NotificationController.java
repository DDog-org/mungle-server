package ddog.groomer.presentation.notification;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.notification.application.NotificationService;
import ddog.notification.application.dto.NotificationResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/groomer/notify")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public SseEmitter connectSseEmitter(PayloadDto payloadDto) {
        return notificationService.connectClient(payloadDto.getAccountId());
    }

    @GetMapping("/all")
    public CommonResponseEntity<List<NotificationResp>> getAllNotifications(PayloadDto payloadDto) {
        return success(notificationService.getAllNotificationsByUserId(payloadDto.getAccountId()));
    }

    @DeleteMapping("/check")
    public CommonResponseEntity<Map<String,Object>> checkOneNotification(@RequestParam Long notificationId, PayloadDto payloadDto) {
        return success(notificationService.checkNotificationById(notificationId));
    }
}
