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

import java.util.Collections;
import java.util.List;
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
            // chatRoomId와 messageId를 함께 사용하는 경우
            QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                            .partitionValue(chatRoomId)  // partition key로 chatRoomId 사용
                            .sortValue(0L) // 예시: sort key로 messageId 사용, 적절한 값 설정 필요
                            .build()))
                    .scanIndexForward(false)  // 내림차순으로 정렬
                    .build();

            List<ChatMessage> messages = getTable()
                    .query(queryRequest)
                    .items()
                    .stream()
                    .map(ChatMessageDynamoEntity::toModel)
                    .collect(Collectors.toList());

            return messages.isEmpty() ? Collections.emptyList() : messages;
        } catch (DynamoDbException e) {
            System.err.println("FETCH FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public ChatMessage findLatestMessageByRoomId(Long chatRoomId) {
        try {
            // Query를 사용하여 가장 최신 메시지 가져오기
            QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                            .partitionValue(chatRoomId)  // Partition Key로 chatRoomId 사용
                            .build()))
                    .scanIndexForward(false) // 내림차순으로 정렬 (최신 메시지가 먼저)
                    .limit(1)  // 가장 최근 메시지 1개만 가져오기
                    .build();

            return getTable().query(queryRequest)
                    .items()
                    .stream()
                    .findFirst()
                    .map(ChatMessageDynamoEntity::toModel)
                    .orElse(null);  // 메시지가 없으면 null 반환
        } catch (DynamoDbException e) {
            System.err.println("FETCH LATEST MESSAGE FAILED: " + e.getMessage());
            throw e;
        }
    }
}