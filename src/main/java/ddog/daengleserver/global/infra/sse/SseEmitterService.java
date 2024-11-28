package ddog.daengleserver.global.infra.sse;

import ddog.daengleserver.application.repository.NotificationRepository;
import ddog.daengleserver.domain.Notification;
import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.usecase.SseEmitterUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;

@Service
@RequiredArgsConstructor

@Slf4j
public class SseEmitterService implements SseEmitterUsecase {

    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final NotificationRepository notificationRepository;

    // Emitter 추가
    public void addEmitter(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);

        // Emitter가 완료되거나 타임아웃되었을 때 처리
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(userId);
        });
    }

    // 특정 사용자에게 메시지 전송
    public void sendMessageToUser(Long userId, String message) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message, MediaType.valueOf("text/event-stream;charset=UTF-8")));
            } catch (IOException e) {
                log.error("사용자 {}에게 데이터 전송 중 IO 오류 발생: {}", userId, e.getMessage(), e);
                removeEmitter(userId);
            } catch (IllegalStateException e) {
                log.error("사용자 {}에 대한 Emitter가 이미 완료된 상태에서 오류 발생: {}", userId, e.getMessage(), e);
                removeEmitter(userId);
            }
        } else {
            log.warn("사용자 {}에 대한 Emitter가 존재하지 않습니다.", userId);
        }
    }

    // 모든 사용자에게 메시지 전송
    public void sendMessageToAllUsers(String message) {
        // 모든 사용자에게 메시지 전송
        for (Long userId : emitters.keySet()) {
            sendMessageToUser(userId, message);
        }
    }

    // 특정 사용자에 대한 Emitter 제거
    public void removeEmitter(Long userId) {
        emitters.remove(userId);
    }

    @Override
    public boolean isUserConnected(Long userId) {
        return emitters.containsKey(userId);
    }

    @Transactional
    public void saveNotificationToDb(NotificationReq notificationReq) {
        notificationRepository.save(Notification.createNotificationWithReq(notificationReq)); // DB에 저장
    }
}
