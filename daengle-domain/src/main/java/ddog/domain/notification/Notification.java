package ddog.domain.notification;

import ddog.domain.notification.enums.NotifyType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Notification {

    private final Long id;
    private final NotifyType notifyType;
    private final String message;
    private final Long userId; // 알림대상

    public static Notification createNotificationWithReq(NotificationReq notificationReq) {
        return Notification.builder()
                .notifyType(notificationReq.getNotifyType())
                .message(notificationReq.getMessage())
                .userId(notificationReq.getUserId())
                .build();
    }
}
