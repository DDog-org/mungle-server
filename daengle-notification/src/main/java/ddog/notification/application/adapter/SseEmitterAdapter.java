package ddog.notification.application.adapter;

import ddog.notification.application.port.EmitterConnectPersist;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterAdapter implements EmitterConnectPersist {

    private final ConcurrentHashMap<Long, SseEmitter> ssemitters = new ConcurrentHashMap<>();

    public SseEmitter toConnectEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter();
        ssemitters.put(userId, emitter);

        emitter.onTimeout(() -> {
            ssemitters.remove(userId);
        });

        emitter.onCompletion(() -> {
            ssemitters.remove(userId);
        });

        return emitter;
    }

    public void sendNotificationToUserByEmitter(Long receiverId, String message) throws IOException {
        if (ssemitters.containsKey(receiverId))  {
            SseEmitter emitter = ssemitters.get(receiverId);
            emitter.send(message);
        }
    }

    public boolean isUserEmitterConnected(Long userId) {
        return ssemitters.containsKey(userId);
    }

}
