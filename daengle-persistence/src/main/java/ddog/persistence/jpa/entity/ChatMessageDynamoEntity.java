package ddog.persistence.jpa.entity;

import ddog.domain.chat.enums.ChatType;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class ChatMessageDynamoEntity {
    @Getter(onMethod_=@DynamoDbPartitionKey)
    private Long chatMessageid;
    private Long chatRommId;
    private ChatType messageType;
    private Long senderId;
    private Long recipientId;
    private Long timestamp;

    @DynamoDbPartitionKey
    public Long getId(){
        return this.getChatMessageid();
    }
}
