package ddog.notification.application.port;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface EmitterConnectPersist {
    SseEmitter toConnectEmitter(Long userId);
    void sendNotificationToUserByEmitter(Long userId, String message) throws IOException;
    boolean isUserEmitterConnected(Long userId);
}
