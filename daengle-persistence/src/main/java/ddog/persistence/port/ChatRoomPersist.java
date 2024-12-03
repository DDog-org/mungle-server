package ddog.persistence.port;

import ddog.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRoomPersist {

    void save(ChatRoom chatRoom);

    List<ChatRoom> findByUserId(Long userId);

    List<ChatRoom> findByPartnerId(Long partnerId);
}
