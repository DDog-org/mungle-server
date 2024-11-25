package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.NotifyMessageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifyMessageJpaRepository extends JpaRepository<NotifyMessageJpaEntity, Long> {
    List<NotifyMessageJpaEntity> findByUserId(Long userId);
}
