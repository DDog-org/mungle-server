package ddog.persistence.jpa.entity;

import ddog.domain.chat.ChatMessage;
import ddog.domain.chat.enums.ChatType;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
@Data
public class ChatMessageDynamoEntity {
    private Long messageId; // 파티션 키
    private Long chatRoomId;    // 정렬 키
    private ChatType messageType;
    private Long senderId;
    private Long recipientId;
    private Long timestamp;
    private String content;

    @DynamoDbPartitionKey
    public Long getMessageId() {
        return messageId;
    }

    @DynamoDbSortKey
    public Long getChatRoomId() {
        return chatRoomId;
    }

    public ChatMessage toModel() {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .messageId(messageId)
                .messageType(messageType)
                .senderId(senderId)
                .recipientId(recipientId)
                .timestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()))
                .content(content)
                .build();
    }

    public static ChatMessageDynamoEntity from(ChatMessage message) {
        return ChatMessageDynamoEntity.builder()
                .chatRoomId(message.getChatRoomId())
                .messageId(message.getMessageId())
                .messageType(message.getMessageType())
                .senderId(message.getSenderId())
                .recipientId(message.getRecipientId())
                .timestamp(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .content(message.getContent())
                .build();
    }
}
