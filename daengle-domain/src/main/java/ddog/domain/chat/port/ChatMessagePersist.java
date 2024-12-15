package ddog.domain.chat.port;

import ddog.domain.chat.ChatMessage;

import java.util.List;

public interface ChatMessagePersist {
    ChatMessage save(ChatMessage message);
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
    ChatMessage findLatestMessageByRoomId(Long chatRoomId);
}
