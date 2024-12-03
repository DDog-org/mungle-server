package ddog.persistence.jpa.repository;

import ddog.domain.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Repository
public interface ChatDynmaoRepository {
    private final DynamoDbTable<ChatMessage> chatMessage;


}
