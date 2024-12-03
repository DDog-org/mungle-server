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
@Getter
@Setter
@DynamoDbBean
public class ChatMessageDynamoEntity {

    private Long chatMessageId; // 파티션 키
    private Long chatRoomId;    // 정렬 키
    private ChatType messageType;
    private Long senderId;
    private Long recipientId;
    private Long timestamp;

    @DynamoDbPartitionKey
    public Long getChatMessageId() {
        return chatMessageId;
    }

    @DynamoDbSortKey
    public Long getChatRoomId() {
        return chatRoomId;
    }

    // 엔티티 → 도메인
    public ChatMessage toModel() {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .messageId(chatMessageId)
                .messageType(messageType)
                .senderId(senderId)
                .recipientId(recipientId)
                .timestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()))
                .build();
    }

    // 도메인 → 엔티티
    public static ChatMessageDynamoEntity from(ChatMessage message) {
        return ChatMessageDynamoEntity.builder()
                .chatRoomId(message.getChatRoomId())
                .chatMessageId(message.getMessageId())
                .messageType(message.getMessageType())
                .senderId(message.getSenderId())
                .recipientId(message.getRecipientId())
                .timestamp(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }
}
