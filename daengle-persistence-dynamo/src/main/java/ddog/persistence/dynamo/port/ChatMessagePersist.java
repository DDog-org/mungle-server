package ddog.persistence.dynamo.port;

import ddog.domain.chat.ChatMessage;

import java.util.List;

public interface ChatMessagePersist {

    // 저장
    ChatMessage save(ChatMessage message);

    // 채팅방 메시지 불러오기
    List<ChatMessage> findByChatRoomId(Long chatRoomId);

}
