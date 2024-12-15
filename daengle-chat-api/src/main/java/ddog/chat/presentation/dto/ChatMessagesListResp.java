package ddog.chat.presentation.dto;

import ddog.domain.chat.enums.ChatType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ChatMessagesListResp {
    private Long roomId;
    private Long userId;
    private Long partnerId;
    private String partnerProfile;
    private Long estimateId;

    private LocalDate dateTime;
    private Map<LocalDate, List<ChatMessageSummary>> messagesGroupedByDate;

    @Getter
    @Builder
    public static class ChatMessageSummary {
        private Long messageId;
        private Long messageSenderId;
        private String messageContent;
        private LocalTime messageTime;
        private ChatType messageType;
    }

}
