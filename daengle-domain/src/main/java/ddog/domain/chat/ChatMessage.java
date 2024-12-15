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
    private ChatType messageType;
    private Long senderId;
    private Long recipientId;
    private LocalDateTime timestamp;
    private String content;

}
