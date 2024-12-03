package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.ChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomJpaEntity, Long> {
    List<ChatRoomJpaEntity> findByUserId(Long userId);

    List<ChatRoomJpaEntity> findByPartnerId(Long partnerId);
}
