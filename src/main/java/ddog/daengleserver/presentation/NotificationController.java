package ddog.daengleserver.presentation;

import ddog.daengleserver.application.CareEstimateService;
import ddog.daengleserver.application.KakaoNotificationService;
import ddog.daengleserver.domain.account.Vet;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final KakaoNotificationService kakaoNotificationService;
    private final Environment environment;
    private final CareEstimateService careEstimateService;

    @PostMapping
    public void sendToUserNotification() {
        Vet vetInfo = careEstimateService.getVetInfo(1L);
        boolean isNotificationSend = kakaoNotificationService.sendOneTalk(vetInfo.getVetName(), vetInfo.getPhoneNumber(), environment.getProperty("templateId.CALL"));
        if (!isNotificationSend) {
            throw new RuntimeException("알림톡 전송 실패");
        } else {
            success("알림톡 전송 성공");
        }
    }
}
