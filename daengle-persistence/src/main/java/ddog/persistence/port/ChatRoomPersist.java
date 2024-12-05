package ddog.persistence.port;

import ddog.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRoomPersist {

    ChatRoom enterChatRoom(Long userId, Long partnerId);

    void exitChatRoom(Long userId, Long partnerId);

}
