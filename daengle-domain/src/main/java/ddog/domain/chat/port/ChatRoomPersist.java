package ddog.domain.chat.port;

import ddog.domain.chat.ChatRoom;

public interface ChatRoomPersist {

    ChatRoom enterChatRoom(Long userId, Long partnerId);

    void exitChatRoom(Long userId, Long partnerId);

}
