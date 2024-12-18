package ddog.persistence.mysql.jpa.entity;

import ddog.domain.chat.ChatRoom;
import ddog.domain.chat.enums.PartnerType;
import jakarta.persistence.*;
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

    @Enumerated(value = EnumType.STRING)
    private PartnerType partnerType;

    public static ChatRoomJpaEntity from(ChatRoom chatRoom) {
        return ChatRoomJpaEntity.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .userId(chatRoom.getUserId())
                .partnerId(chatRoom.getPartnerId())
                .partnerType(chatRoom.getPartnerType())
                .build();
    }

    public ChatRoom toModel() {
        return ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .partnerId(partnerId)
                .partnerType(partnerType)
                .build();
    }
}
