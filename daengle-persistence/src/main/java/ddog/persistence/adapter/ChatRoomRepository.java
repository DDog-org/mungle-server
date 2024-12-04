package ddog.persistence.adapter;

import ddog.domain.chat.ChatRoom;
import ddog.persistence.jpa.entity.ChatRoomJpaEntity;
import ddog.persistence.jpa.repository.ChatRoomJpaRepository;
import ddog.persistence.port.ChatRoomPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
