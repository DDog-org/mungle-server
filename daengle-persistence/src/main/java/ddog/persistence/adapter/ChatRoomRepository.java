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
    public void save(ChatRoom chatRoom) {
        chatRoomJpaRepository.save(ChatRoomJpaEntity.from(chatRoom));
    }

    @Override
    public List<ChatRoom> findByUserId(Long userId) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (ChatRoomJpaEntity chatRoomJpaEntity : chatRoomJpaRepository.findByUserId(userId)) {
            chatRooms.add(chatRoomJpaEntity.toModel());
        }
        return chatRooms;
    }


    @Override
    public List<ChatRoom> findByPartnerId(Long partnerId) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (ChatRoomJpaEntity chatRoomJpaEntity : chatRoomJpaRepository.findByPartnerId(partnerId)) {
            chatRooms.add(chatRoomJpaEntity.toModel());
        }
        return chatRooms;
    }
}
