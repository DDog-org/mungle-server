package ddog.persistence.mysql.adapter;

import ddog.domain.chat.ChatRoom;
import ddog.domain.chat.enums.PartnerType;
import ddog.persistence.mysql.jpa.entity.ChatRoomJpaEntity;
import ddog.persistence.mysql.jpa.repository.ChatRoomJpaRepository;
import ddog.domain.chat.port.ChatRoomPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository implements ChatRoomPersist {

    private final ChatRoomJpaRepository chatRoomJpaRepository;

    @Override
    public ChatRoom enterChatRoom(Long userId, Long partnerId, PartnerType partnerType) {

        if (chatRoomJpaRepository.findByUserIdAndPartnerId(userId, partnerId) == null) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .userId(userId)
                    .partnerId(partnerId)
                    .partnerType(partnerType)
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
        chatRoomJpaRepository.deleteById(findRoom.getChatRoomId());
    }

    @Override
    public ChatRoom findByUserIdPartnerId(Long userId, Long partnerId) {
        return chatRoomJpaRepository.findByUserIdAndPartnerId(userId, partnerId).toModel();
    }

    @Override
    public ChatRoom findByRoomId(Long roomId) {
        return chatRoomJpaRepository.findByChatRoomId(roomId).toModel();
    }

    @Override
    public List<ChatRoom> findByUserId(Long userId) {
        return chatRoomJpaRepository.findByUserId(userId).stream().map(ChatRoomJpaEntity::toModel).toList();
    }

    @Override
    public List<ChatRoom> findByPartnerId(Long partnerId) {
        return chatRoomJpaRepository.findByPartnerId(partnerId).stream().map(ChatRoomJpaEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoom> findByUserIdAndPartnerType(Long userId, PartnerType partnerType) {
        return chatRoomJpaRepository.findAllByUserIdAndPartnerType(userId, partnerType).stream().map(ChatRoomJpaEntity::toModel).toList();
    }
}
