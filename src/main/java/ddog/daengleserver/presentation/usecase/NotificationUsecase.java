package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.domain.Notification;
import ddog.daengleserver.presentation.notify.dto.NotificationReq;

import java.util.List;

public interface NotificationUsecase {
    void sendNotificationToUser(NotificationReq notificationReq);
    void sendNotificationToAllUser(String message);
    void sendNotifcationToUserByRedis(Long userId, String message);
    List<Notification> getNotificationsForLogoutUser(Long userId);
}
