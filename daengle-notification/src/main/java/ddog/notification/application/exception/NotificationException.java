package ddog.notification.application.exception;

import ddog.auth.exception.common.CustomRuntimeException;

public class NotificationException extends CustomRuntimeException {
    public NotificationException(NotificationType type, Object... args) {
        super(type.getMessage(), type.getHttpStatus(), type.getCode());
    }
}
