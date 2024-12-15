package ddog.chat.presentation.dto;

import ddog.domain.chat.enums.ChatType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageReq {

    private Long senderId;
    private String messageContent;
    private ChatType messageType;
}
