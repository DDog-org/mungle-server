package ddog.domain.chat;

import ddog.domain.chat.enums.PartnerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private Long chatRoomId;
    private Long userId;
    private Long partnerId;
    private PartnerType partnerType;
}
