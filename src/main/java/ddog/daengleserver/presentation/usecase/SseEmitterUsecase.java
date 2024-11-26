package ddog.daengleserver.presentation.usecase;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterUsecase {

    void addEmitter(Long userId, SseEmitter emitter);
    void sendMessageToUser(Long userId, String message);
    void sendMessageToAllUsers(String message);
    void removeEmitter(Long userId);
    boolean isUserConnected(Long userId);
}
