package ddog.persistence.rdb.jpa.repository;


import ddog.persistence.rdb.jpa.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    Optional<OrderJpaEntity> findByOrderUid(String orderUid);
    Optional<OrderJpaEntity> findByIdempotencyKey(String orderUid);
}
