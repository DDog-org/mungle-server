package ddog.chat.presentation.dto;

import ddog.domain.chat.enums.ChatType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ChatMessagesListResp {
    private Long roomId;
    private Long userId;
    private Long otherId;
    private String otherProfile;
    private String otherName;
    private Long estimateId;

    private List<Map<String, Object>> messagesGroupedByDate;

    @Getter
    @Builder
    public static class ChatMessageSummary {
        private Long messageId;
        private Long messageSenderId;
        private String messageContent;
        private LocalDateTime messageTime;
        private ChatType messageType;
    }
}
