package ddog.daengleserver.application;

import ddog.daengleserver.presentation.dto.request.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageService {
    KakaoPushService kakaoPushService;

    public boolean sendMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setBtnTitle("자세히 보기");
        messageDto.setMobileUrl("");
        messageDto.setObjType("text");
        messageDto.setWebUrl("");
        messageDto.setText("메시지 테스트");

        String accessToken = KakaoAuthService.authToken;

        return kakaoPushService.sendMessage(accessToken, messageDto);
    }

}
