package ddog.persistence.mysql.jpa.repository;


import ddog.persistence.mysql.jpa.entity.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, Long> {
    List<NotificationJpaEntity> findByUserId(Long userId);

}
