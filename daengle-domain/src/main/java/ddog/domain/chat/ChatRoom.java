package ddog.domain.chat;

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
    private Long userId; // 일반 사용자 id
    private Long partnerId; // 미용사 or 수의사 id
}
