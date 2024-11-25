package ddog.daengleserver.presentation.notify.dto;

import ddog.daengleserver.presentation.notify.enums.NotifyType;
import lombok.Getter;

@Getter
public class NotificationReq {
    private NotifyType notifyType;
    private String message;
    private Long userId;

    public NotificationReq(NotifyType notifyType, String message, long userId) {
        this.notifyType = notifyType;
        this.message = message;
        this.userId = userId;
    }
}
