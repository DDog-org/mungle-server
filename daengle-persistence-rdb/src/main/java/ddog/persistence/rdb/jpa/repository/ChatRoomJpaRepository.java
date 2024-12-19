package ddog.persistence.rdb.jpa.repository;

import ddog.domain.chat.enums.PartnerType;
import ddog.persistence.rdb.jpa.entity.ChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomJpaEntity, Long> {
    List<ChatRoomJpaEntity> findByUserId(Long userId);

    List<ChatRoomJpaEntity> findByPartnerId(Long partnerId);

    ChatRoomJpaEntity findByUserIdAndPartnerId(Long userId, Long partnerId);

    ChatRoomJpaEntity findByChatRoomId(Long chatRoomId);

    List<ChatRoomJpaEntity> findAllByUserIdAndPartnerType(Long userId, PartnerType partnerType);
}
