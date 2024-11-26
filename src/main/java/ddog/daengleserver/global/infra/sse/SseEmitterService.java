package ddog.daengleserver.global.infra.sse;

import ddog.daengleserver.presentation.usecase.SseEmitterUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;

@Service
@Slf4j
public class SseEmitterService implements SseEmitterUsecase {

    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // Emitter 추가
    public void addEmitter(Long userId, SseEmitter emitter) {
        removeInvalidEmitters(); // 유효하지 않은 Emitter 정리
        emitters.put(userId, emitter); // 사용자 ID와 Emitter를 맵에 추가

        // Emitter가 완료되거나 타임아웃되었을 때 처리
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(userId);
        });
    }

    // 특정 사용자에게 메시지 전송
    public void sendMessageToUser(Long userId, String message) {
        removeInvalidEmitters();

        SseEmitter emitter = emitters.get(userId); // 사용자 ID에 해당하는 Emitter 찾기
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message, MediaType.valueOf("text/event-stream;charset=UTF-8")));

            } catch (IOException e) {
                log.error("사용자 {}에게 데이터 전송 중 오류 발생: {}", userId, e.getMessage(), e);
                removeEmitter(userId);
            } catch (IllegalStateException e) {
                log.error("사용자 {}에 대한 완료된 Emitter 오류 발생: {}", userId, e.getMessage(), e);
                removeEmitter(userId);
            }
        } else {
            log.warn("사용자 {}에 대한 Emitter가 존재하지 않습니다.", userId);
        }
    }

    // 모든 사용자에게 메시지 전송
    public void sendMessageToAllUsers(String message) {
        removeInvalidEmitters();

        // 모든 사용자에게 메시지 전송
        for (Long userId : emitters.keySet()) {
            sendMessageToUser(userId, message);
        }
    }

    // 특정 사용자에 대한 Emitter 제거
    public void removeEmitter(Long userId) {
        if (userId != null) {
            emitters.remove(userId);
        }
    }

    @Override
    public boolean isUserConnected(Long userId) {
        return emitters.containsKey(userId);
    }

    // 유효하지 않은 Emitter 정리
    private void removeInvalidEmitters() {
        emitters.entrySet().removeIf(entry -> isInvalidEmitter(entry.getValue()));
    }

    // Emitter 유효성 검사
    private boolean isInvalidEmitter(SseEmitter emitter) {
        if (emitter == null) {
            return true; // Emitter가 null이면 유효하지 않음
        }
        try {
            emitter.send(""); // Emitter의 유효성 검사
            return false; // 유효한 Emitter는 유지
        } catch (IOException e) {
            return true; // 오류가 발생하면 유효하지 않음
        }
    }
}

