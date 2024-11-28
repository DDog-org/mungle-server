package ddog.daengleserver.presentation.notify.dto;

import ddog.daengleserver.presentation.notify.enums.NotifyType;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
