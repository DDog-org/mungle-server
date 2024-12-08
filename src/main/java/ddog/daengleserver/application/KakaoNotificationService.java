package ddog.daengleserver.application;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

@Service
public class KakaoNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(KakaoNotificationService.class);

    private final DefaultMessageService messageService;

    @Value("${kakao.pfId}")
    private String kakaoPfid;

    @Value("${kakao.setFrom}")
    private String kakaoFrom;

    public KakaoNotificationService(@Value("${kakao.apiKey}") String kakaoApiKey,
                                    @Value("${kakao.apiSecretKey}") String kakaoApiSecretKey,
                                    @Value("${kakao.domain}") String kakaoDomain) {
        this.messageService = NurigoApp.INSTANCE.initialize(kakaoApiKey, kakaoApiSecretKey, kakaoDomain);
    }
    private void executeNotification(String userName, String userPhoneNumber, String template) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(() -> {
            simulateDelay(); // 10초 지연 발생
            sendKakaoMessage(userName, userPhoneNumber, template);
            return null;
        });

        try {
            future.get(5, TimeUnit.SECONDS); // 타임아웃 5초 설정 => 5초 이상의 처리 시간 시, 예외처리
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout: 알림톡 전송 요청이 5초를 초과했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("알림톡 전송 중 오류 발생", e);
        } finally {
            executor.shutdownNow();
        }
    }

    private void sendKakaoMessage(String userName, String userPhoneNumber, String template) {
        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setDisableSms(true);
        kakaoOption.setPfId(kakaoPfid);
        kakaoOption.setTemplateId(template);

        HashMap<String, String> variables = new HashMap<>();
        variables.put("#{사용자}", userName);
        kakaoOption.setVariables(variables);

        Message messageToSend = new Message();
        messageToSend.setFrom(kakaoFrom);
        messageToSend.setTo(userPhoneNumber);
        messageToSend.setKakaoOptions(kakaoOption);

        this.messageService.sendOne(new SingleMessageSendingRequest(messageToSend));
    }

    private void simulateDelay() {
        try {
            Thread.sleep(7000); // 10초 지연
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @CircuitBreaker(name = "kakaoNotificationService", fallbackMethod = "fallbackSendOneTalk")
    public boolean sendOneTalk(String userName, String userPhoneNumber, String template) {
        try {
            executeNotification(userName, userPhoneNumber, template);
            return true; // 성공 시 true 반환
        } catch (Exception e) {
            throw new RuntimeException("카카오 알림톡 전송 실패", e);
        }
    }

    public boolean fallbackSendOneTalk(String userName, String userPhoneNumber, String template, Throwable throwable) {
        logger.error("Fallback activated: 알림톡 전송 실패. 사용자: {}, 오류: {}", userName, throwable.getMessage());
        return false;
    }
}
