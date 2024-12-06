package ddog.notification.application;

import ddog.domain.notification.Notification;
import ddog.domain.notification.enums.NotifyType;
import ddog.persistence.mysql.port.NotificationPersist;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationService {

    private final SseService sseService;
    private final RedisService redisService;
    private final NotificationPersist notificationPersist;

    public NotificationService(SseService sseService, RedisService redisService, NotificationPersist notificationPersist) {
        this.sseService = sseService;
        this.redisService = redisService;
        this.notificationPersist = notificationPersist;
    }

    public void sendNotifiacationToUser(Long receiverId, NotifyType notifyType, String message) {
        try {
            if (sseService.isUserSseConnected(receiverId)) {
                sseService.sendNotificationToUserBySseEmitter(receiverId, message);
            } else if (redisService.isUserLoggedIn(receiverId)) {
                sseService.toConnectSseEmitter(receiverId);
                sseService.sendNotificationToUserBySseEmitter(receiverId, message);
            } else {
                Notification notification = Notification.builder()
                        .userId(receiverId)
                        .message(message)
                        .notifyType(notifyType)
                        .build();

                notificationPersist.saveNotificationWithLogoutUser(notification);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to send notification", e);
        }
    }

    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return notificationPersist.findNotificationsByUserId(userId);
    }
}
