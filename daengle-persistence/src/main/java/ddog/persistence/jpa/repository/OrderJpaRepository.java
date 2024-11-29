package ddog.persistence.jpa.repository;

import ddog.daengleserver.infrastructure.po.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    Optional<OrderJpaEntity> findByOrderUid(String orderUid);
}
