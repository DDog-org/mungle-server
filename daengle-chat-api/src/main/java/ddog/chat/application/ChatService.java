package ddog.chat.application;

import ddog.domain.chat.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    // 채팅방 생성
    public void startChatWith(Long userId, Long partnerId) {

    }

    // 채팅방 입장 - 데이터 불러오기
    public void enterChat(Long roomId){

    }

    // 채팅방 나가기 - 데이터 삭제 및 방 삭제
    public void endChat(Long roomId){

    }


    // 메시지 전송 - 사진 or 텍스트
    public void saveMessage(Long roomId, ChatMessage message) {
    }
}
