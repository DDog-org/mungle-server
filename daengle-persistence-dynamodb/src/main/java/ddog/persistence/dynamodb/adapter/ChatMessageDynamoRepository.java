package ddog.persistence.dynamodb.adapter;

import ddog.domain.chat.ChatMessage;

import ddog.domain.chat.port.ChatMessagePersist;
import ddog.persistence.dynamodb.entity.ChatMessageDynamoEntity;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatMessageDynamoRepository implements ChatMessagePersist {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private static final String TABLE_NAME = "chat_message";

    private DynamoDbTable<ChatMessageDynamoEntity> getTable() {
        return dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(ChatMessageDynamoEntity.class));
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        ChatMessageDynamoEntity entity = ChatMessageDynamoEntity.from(message);

        try {
            getTable().putItem(entity);
            System.out.println("SAVE SUCCESS");

        } catch (DynamoDbException e) {
            System.err.println("SAVE FAILED" + e.getMessage());
            throw e;
        }

        return message;
    }

    @Override
    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        try {
            QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                            .partitionValue(chatRoomId)
                            .build()))
                    .scanIndexForward(false)
                    .build();

            List<ChatMessage> messages = getTable()
                    .query(queryRequest)
                    .items()
                    .stream()
                    .map(ChatMessageDynamoEntity::toModel)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                return Collections.emptyList();
            }

            Map<LocalDate, List<ChatMessage>> groupedMessages = messages.stream()
                    .collect(Collectors.groupingBy(
                            message -> message.getTimestamp().toLocalDate()
                    ));

            groupedMessages.forEach((date, messageList) -> {
                messageList.sort(Comparator.comparing(ChatMessage::getTimestamp));
            });

            List<ChatMessage> sortedMessages = groupedMessages.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .collect(Collectors.toList());

            return sortedMessages;

        } catch (DynamoDbException e) {
            System.err.println("FETCH FAILED: " + e.getMessage());
            throw e;
        }
    }


    @Override
    public ChatMessage findLatestMessageByRoomId(Long chatRoomId) {
        try {
            QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                            .partitionValue(chatRoomId)
                            .build()))
                    .scanIndexForward(false)
                    .limit(1)
                    .build();

            return getTable().query(queryRequest)
                    .items()
                    .stream()
                    .findFirst()
                    .map(ChatMessageDynamoEntity::toModel)
                    .orElse(null);
        } catch (DynamoDbException e) {
            System.err.println("FETCH LATEST MESSAGE FAILED: " + e.getMessage());
            throw e;
        }
    }
}