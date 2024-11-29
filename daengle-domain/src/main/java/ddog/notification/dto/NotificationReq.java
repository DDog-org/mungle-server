package ddog.notification.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddog.notification.enums.NotifyType;
import lombok.Getter;


@Getter
public class NotificationReq {
    private NotifyType notifyType;
    private String message;
    private Long userId;

    @JsonCreator
    public NotificationReq(@JsonProperty("notifyType") NotifyType notifyType,
                           @JsonProperty("message") String message,
                           @JsonProperty("userId") Long userId) {
        this.notifyType = notifyType;
        this.message = message;
        this.userId = userId;
    }
}
