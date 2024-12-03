package ddog.domain.chat;

import ddog.domain.chat.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private Long chatRoomId;
    private Long messageId;
    private ChatType messageType; // 기본 메시지 or 사진
    private Long senderId; // 보내는 사람
    private Long recipientId; // 받는 사람
    private LocalDateTime timestamp;

}
