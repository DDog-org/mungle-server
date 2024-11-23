package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}
