package ddog.chat.application;

import ddog.domain.chat.ChatMessage;
import ddog.persistence.dynamo.port.ChatMessagePersist;
import ddog.persistence.mysql.port.ChatRoomPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessagePersist chatMessagePersist;
    private final ChatRoomPersist chatRoomPersist;

    // 채팅방 입장 or 생성
    public void enterChat(Long userId, Long partnerId){
        chatRoomPersist.enterChatRoom(userId, partnerId);
    }

    // 채팅방 나가기 - 데이터 삭제 및 방 삭제
    public void endChat(Long userId, Long partnerId){
        chatRoomPersist.exitChatRoom(userId, partnerId);
    }


    // 메시지 전송 - 사진 or 텍스트
    public ChatMessage saveMessage(Long roomId, ChatMessage message) {
        return chatMessagePersist.save(message);
    }
}
