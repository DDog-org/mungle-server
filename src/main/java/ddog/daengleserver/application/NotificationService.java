package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.NotificationRepository;
import ddog.daengleserver.domain.Notification;
import ddog.daengleserver.global.infra.sse.SseEmitterService;
import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.usecase.NotificationUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUsecase {
    private final StringRedisTemplate stringRedisTemplate;
    private final SseEmitterService sseEmitterService;
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotificationToUser(NotificationReq notificationReq) {
        if (isUserLoggedIn(notificationReq.getUserId())) {
            sseEmitterService.sendMessageToUser(notificationReq.getUserId(), notificationReq.getMessage());
        } else {
            saveNotificationToDb(notificationReq);
        }
    }

    @Override
    public void sendNotificationToAllUser(String message) {
        stringRedisTemplate.convertAndSend("all_users", message);
    }

    @Override
    public void sendNotifcationToUserByRedis(Long userId, String message) {
        stringRedisTemplate.convertAndSend("user_logged_in:" + userId, message);
    }

    @Override
    public List<Notification> getNotificationsForLogoutUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    // 사용자 로그인 상태 체크
    private boolean isUserLoggedIn(Long userId) {
        return stringRedisTemplate.hasKey("user_logged_in:" + userId); // Redis에서 로그인 여부 체크
    }

    // DB에 알림 메시지 저장
    @Transactional
    public void saveNotificationToDb(NotificationReq notificationReq) {
        notificationRepository.save(Notification.createNotificationWithReq(notificationReq)); // DB에 저장
    }
}
