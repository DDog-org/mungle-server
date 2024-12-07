package ddog.notification.application;

import ddog.domain.notification.Notification;
import ddog.domain.notification.enums.NotifyType;
import ddog.notification.application.adapter.SseEmitterManager;
import ddog.notification.application.adapter.RedisUserStatusManager;
import ddog.notification.application.exception.NotificationException;
import ddog.notification.application.exception.NotificationExceptionType;
import ddog.notification.application.port.EmitterConnectPersist;
import ddog.notification.application.port.UserStatusPersist;
import ddog.persistence.mysql.port.NotificationPersist;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationService {

    private final EmitterConnectPersist emitterConnectPersist;
    private final UserStatusPersist userStatusPersist;
    private final NotificationPersist notificationPersist;

    public NotificationService(SseEmitterManager emitterConnectPersist, RedisUserStatusManager userStatusPersist, NotificationPersist notificationPersist) {
        this.emitterConnectPersist = emitterConnectPersist;
        this.userStatusPersist = userStatusPersist;
        this.notificationPersist = notificationPersist;
    }

    public void sendNotifiacationToUser(Long receiverId, NotifyType notifyType, String message) {
        try {
            if (receiverId == null || message == null || notifyType == null) {
                throw new NotificationException(NotificationExceptionType.ALERT_CAN_NOT, "Invalid parameters provided");
            }

            if (emitterConnectPersist.isUserEmitterConnected(receiverId)) {
                emitterConnectPersist.sendNotificationToUserByEmitter(receiverId, message);
            } else if (userStatusPersist.isUserLoggedIn(receiverId)) {
                emitterConnectPersist.toConnectEmitter(receiverId);
                emitterConnectPersist.sendNotificationToUserByEmitter(receiverId, message);
            } else {
                Notification notification = Notification.builder()
                        .userId(receiverId)
                        .message(message)
                        .notifyType(notifyType)
                        .build();

                notificationPersist.saveNotificationWithLogoutUser(notification);
            }
        } catch (IOException e) {
            throw new NotificationException(NotificationExceptionType.ALERT_CAN_NOT, e.getMessage());
        }
    }

    public List<Notification> getAllNotificationsByUserId(Long userId) {
        if (userId == null) {
            throw new NotificationException(NotificationExceptionType.ALERT_CAN_NOT, "User ID cannot be null");
        }

        return notificationPersist.findNotificationsByUserId(userId);
    }
}
