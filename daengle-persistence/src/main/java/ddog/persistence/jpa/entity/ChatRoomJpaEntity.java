package ddog.persistence.jpa.entity;

import ddog.domain.chat.ChatRoom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ChatRooms")
public class ChatRoomJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    private Long userId;
    private Long partnerId;

    public static ChatRoomJpaEntity from(ChatRoom chatRoom) {
        return ChatRoomJpaEntity.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .userId(chatRoom.getUserId())
                .partnerId(chatRoom.getPartnerId())
                .build();
    }

    public ChatRoom toModel() {
        return ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .partnerId(partnerId)
                .build();
    }
}
