package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
