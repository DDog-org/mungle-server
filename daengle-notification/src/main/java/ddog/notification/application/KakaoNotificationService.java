package ddog.notification.application;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class KakaoNotificationService {
    private final DefaultMessageService messageService;

    @Value("${kakao.pfId}")
    private String kakaoPfid;

    @Value("${kakao.templateId}")
    private String kakaoTemplateId;

    @Value("${kakao.setFrom}")
    private String kakaoFrom;

    public KakaoNotificationService(@Value("${kakao.apiKey}") String kakaoApiKey,
                                    @Value("${kakao.apiSecretKey}") String kakaoApiSecretKey,
                                    @Value("${kakao.domain}") String kakaoDomain) {
        this.messageService = NurigoApp.INSTANCE.initialize(kakaoApiKey, kakaoApiSecretKey, kakaoDomain);
    }

    public void sendOneTalk(String userName, String userPhoneNumber) {
        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setDisableSms(true);

        kakaoOption.setPfId(kakaoPfid);
        kakaoOption.setTemplateId(kakaoTemplateId);

        HashMap<String, String> variables = new HashMap<>();
        variables.put("#{사용자}", userName);

        kakaoOption.setVariables(variables);

        Message messageTosend = new Message();
        messageTosend.setFrom(kakaoFrom);
        messageTosend.setTo(userPhoneNumber);

        messageTosend.setKakaoOptions(kakaoOption);

        this.messageService.sendOne(
                new SingleMessageSendingRequest(messageTosend)
        );
    }
}
