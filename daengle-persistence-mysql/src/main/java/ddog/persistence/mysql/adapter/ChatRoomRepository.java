package ddog.persistence.mysql.adapter;

import ddog.domain.chat.ChatRoom;
import ddog.persistence.mysql.jpa.entity.ChatRoomJpaEntity;
import ddog.persistence.mysql.jpa.repository.ChatRoomJpaRepository;
import ddog.domain.chat.port.ChatRoomPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository implements ChatRoomPersist {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    @Override
    public ChatRoom enterChatRoom(Long userId, Long partnerId) {
        if (chatRoomJpaRepository.findByUserIdAndPartnerId(userId,partnerId) != null) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .userId(userId)
                    .partnerId(partnerId)
                    .build();
            return chatRoomJpaRepository.save(ChatRoomJpaEntity.from(chatRoom)).toModel();
        }
        else {
            return chatRoomJpaRepository.findByUserIdAndPartnerId(userId, partnerId).toModel();
        }
    }

    @Override
    public void exitChatRoom(Long userId, Long partnerId) {
        ChatRoom findRoom = chatRoomJpaRepository.findByUserIdAndPartnerId(userId, partnerId).toModel();
        chatRoomJpaRepository.delete(ChatRoomJpaEntity.from(findRoom));
    }
}
