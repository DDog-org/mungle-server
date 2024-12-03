package ddog.persistence.adapter;

import ddog.domain.chat.ChatMessage;
import ddog.persistence.port.ChatMessagePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class ChatMessageRepository implements ChatMessagePersist {
    @Override
    public ChatMessage save(ChatMessage message) {
        return null;
    }

    @Override
    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        return null;
    }
}
