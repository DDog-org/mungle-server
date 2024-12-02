package ddog.notification.application;

import ddog.domain.notification.Notification;
import ddog.domain.notification.enums.NotifyType;
import ddog.persistence.port.NotificationPersist;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    // 연결된 SseEmitter 목록
    private final ConcurrentHashMap<Long, SseEmitter> ssemitters = new ConcurrentHashMap<>();

    private final RedisService redisService;

    private final NotificationPersist notificationPersist;

    public NotificationService(RedisService redisService, NotificationPersist notificationPersist) {
        this.redisService = redisService;
        this.notificationPersist = notificationPersist;
    }

    // SSE 연결
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter();
        ssemitters.put(userId, emitter);

        redisService.loginUser(userId);

        emitter.onTimeout(() -> {
            ssemitters.remove(userId);
            redisService.logoutUser(userId);

        });
        emitter.onCompletion(() -> {
            ssemitters.remove(userId);
            redisService.logoutUser(userId);
        });

        return emitter;
    }

    // 알림 전송
    public void sendNotification(Long receiverId, NotifyType notifyType, String message) throws IOException {
        if (ssemitters.containsKey(receiverId)) {
            SseEmitter emitter = ssemitters.get(receiverId);
            emitter.send(message);
        }

        else if (redisService.isUserLoggedIn(receiverId)) {
            SseEmitter emitter = ssemitters.get(receiverId);
            if (emitter != null) {
                emitter.send(message);
            }
        }

        else {
            Notification notification = Notification.builder()
                    .userId(receiverId)
                    .message(message)
                    .notifyType(notifyType)
                    .build();

            notificationPersist.save(notification);
        }
    }

    // 로그인 시, 알림 목록 불러오기
    public List<Notification> getAllNotifications(Long userId) {
        return notificationPersist.findByUserId(userId);
    }
}
