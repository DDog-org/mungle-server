package ddog.notification.application;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final ConcurrentHashMap<Long, SseEmitter> ssemitters = new ConcurrentHashMap<>();

    public SseEmitter toConnectSseEmitter(Long userId) {
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

    public void sendNotificationToUserBySseEmitter(Long receiverId, String message) throws IOException {
        if (ssemitters.containsKey(receiverId))  {
            SseEmitter emitter = ssemitters.get(receiverId);
            emitter.send(message);
        }
    }

    public boolean isUserSseConnected(Long userId) {
        return ssemitters.containsKey(userId);
    }

}
