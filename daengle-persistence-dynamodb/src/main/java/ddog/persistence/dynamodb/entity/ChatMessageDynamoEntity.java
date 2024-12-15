package ddog.persistence.dynamodb.entity;

import ddog.domain.chat.ChatMessage;
import ddog.domain.chat.enums.ChatType;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
@Data
public class ChatMessageDynamoEntity {
    private Long messageId;
    private Long chatRoomId;
    private ChatType messageType;
    private Long senderId;
    private Long recipientId;
    private String timestamp;
    private String content;

    @DynamoDbSortKey
    public Long getMessageId() {
        return messageId;
    }

    @DynamoDbPartitionKey
    public Long getChatRoomId() {
        return chatRoomId;
    }

    public ChatMessage toModel() {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // ISO_LOCAL_DATE_TIME 사용
        LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp, formatter); // 파싱

        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .messageId(messageId)
                .messageType(messageType)
                .senderId(senderId)
                .recipientId(recipientId)
                .timestamp(parsedTimestamp)
                .content(content)
                .build();
    }

    public static ChatMessageDynamoEntity from(ChatMessage message) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


        return ChatMessageDynamoEntity.builder()
                .chatRoomId(message.getChatRoomId())
                .messageId(message.getMessageId())
                .messageType(message.getMessageType())
                .senderId(message.getSenderId())
                .recipientId(message.getRecipientId())
                .timestamp(message.getTimestamp().format(formatter))  // ISO_LOCAL_DATE_TIME으로 저장
                .content(message.getContent())
                .build();
    }
}
