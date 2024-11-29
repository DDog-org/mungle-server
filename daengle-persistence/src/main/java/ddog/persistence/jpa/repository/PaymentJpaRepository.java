package ddog.persistence.jpa.repository;

import ddog.daengleserver.infrastructure.po.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
