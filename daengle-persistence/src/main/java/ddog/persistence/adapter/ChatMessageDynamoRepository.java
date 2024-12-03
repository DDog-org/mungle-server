package ddog.persistence.jpa.repository;

import ddog.domain.chat.ChatMessage;
import ddog.persistence.jpa.entity.ChatMessageDynamoEntity;
import ddog.persistence.port.ChatMessagePersist;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChatMessageDynamoRepository implements ChatMessagePersist {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbClient dynamoDbClient;

    public ChatMessageDynamoRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbClient dynamoDbClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbClient = dynamoDbClient;
    }

    // DynamoDB 테이블 생성 메서드
    private void createTableIfNotExists() {
        try {
            describeTable();
            System.out.println("Table already exists.");
        } catch (ResourceNotFoundException e) {
            createNewTable();
        }
    }

    // 테이블 존재 여부 확인
    private void describeTable() {
        DescribeTableRequest describeTableRequest = DescribeTableRequest.builder()
                .tableName("ChatMessages")
                .build();

        try {
            dynamoDbClient.describeTable(describeTableRequest);
            System.out.println("Table exists.");
        } catch (ResourceNotFoundException e) {
            System.out.println("Table does not exist.");
            throw e;
        } catch (DynamoDbException e) {
            System.err.println("Error checking table existence: " + e.getMessage());
        }
    }

    // 테이블 생성
    private void createNewTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName("ChatMessages")
                .keySchema(
                        KeySchemaElement.builder().attributeName("chatMessageId").keyType(KeyType.HASH).build(),
                        KeySchemaElement.builder().attributeName("chatRoomId").keyType(KeyType.RANGE).build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("chatMessageId").attributeType(ScalarAttributeType.N).build(),
                        AttributeDefinition.builder().attributeName("chatRoomId").attributeType(ScalarAttributeType.N).build()
                )
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();

        try {
            dynamoDbClient.createTable(createTableRequest);
            System.out.println("Table created successfully.");
        } catch (DynamoDbException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    // DynamoDB 테이블 접근
    private DynamoDbTable<ChatMessageDynamoEntity> getTable() {
        return dynamoDbEnhancedClient.table("ChatMessages", TableSchema.fromBean(ChatMessageDynamoEntity.class));
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        createTableIfNotExists();  // 테이블이 존재하지 않으면 생성
        ChatMessageDynamoEntity entity = ChatMessageDynamoEntity.from(message);

        // DynamoDB에 메시지 저장
        getTable().putItem(entity);
        return message;
    }

    @Override
    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        // DynamoDB에서 채팅방 ID로 메시지 조회
        return getTable()
                .scan()  // 테이블 스캔
                .items()
                .stream()
                .filter(item -> chatRoomId.equals(item.getChatRoomId()))  // chatRoomId로 필터링
                .map(ChatMessageDynamoEntity::toModel)  // 엔티티를 모델로 변환
                .collect(Collectors.toList());
    }
}