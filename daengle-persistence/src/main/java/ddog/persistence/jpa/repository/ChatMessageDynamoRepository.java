package ddog.persistence.jpa.repository;

import ddog.domain.chat.ChatMessage;
import ddog.persistence.jpa.entity.ChatMessageDynamoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageDynamoRepository extends CrudRepository<ChatMessageDynamoEntity, Long> {

    List<ChatMessageDynamoEntity> findByChatRoomId(Long chatRoomId);

    ChatMessage save();

}
