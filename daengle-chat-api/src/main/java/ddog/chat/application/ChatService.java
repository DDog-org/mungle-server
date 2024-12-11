package ddog.chat.application;

import ddog.domain.chat.ChatMessage;
import ddog.domain.chat.port.ChatMessagePersist;
import ddog.domain.chat.port.ChatRoomPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessagePersist chatMessagePersist;
    private final ChatRoomPersist chatRoomPersist;

    public void enterChat(Long userId, Long partnerId){
        chatRoomPersist.enterChatRoom(userId, partnerId);
    }

    public void endChat(Long userId, Long partnerId){
        chatRoomPersist.exitChatRoom(userId, partnerId);
    }


    public ChatMessage saveMessage(Long roomId, ChatMessage message) {
        return chatMessagePersist.save(message);
    }

    public List<ChatMessage> findMessagesByRoomId(Long roomId) {
        return chatMessagePersist.findByChatRoomId(roomId);
    }
}
