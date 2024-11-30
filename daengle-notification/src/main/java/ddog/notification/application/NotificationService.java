package ddog.notification.application;


import ddog.domain.notification.Notification;
import ddog.domain.notification.NotificationReq;
import ddog.persistence.adapter.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService  {
    private final StringRedisTemplate stringRedisTemplate;
    private final SseEmitterService sseEmitterService;
    private final NotificationRepository notificationRepository;


    public void sendNotificationToUser(NotificationReq notificationReq) {
        if (isUserLoggedIn(notificationReq.getUserId())) {
            sseEmitterService.sendMessageToUser(notificationReq.getUserId(), notificationReq.getMessage());
        } else {
            saveNotificationToDb(notificationReq);
        }
    }

    public void sendNotificationToAllUser(String message) {
        stringRedisTemplate.convertAndSend("all_users", message);
    }

    public void sendNotifcationToUserByRedis(Long userId, String message) {
        stringRedisTemplate.convertAndSend("user_logged_in:" + userId, message);
    }

    public List<Notification> getNotificationsForLogoutUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    // 사용자 로그인 상태 체크
    private boolean isUserLoggedIn(Long userId) {
        return stringRedisTemplate.hasKey("user_logged_in:" + userId); // Redis에서 로그인 여부 체크
    }

    // DB에 알림 메시지 저장
    private void saveNotificationToDb(NotificationReq notificationReq) {
        notificationRepository.save(Notification.createNotificationWithReq(notificationReq)); // DB에 저장
    }
}
