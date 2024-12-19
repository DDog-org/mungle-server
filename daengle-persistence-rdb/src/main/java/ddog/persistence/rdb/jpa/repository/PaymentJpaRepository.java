package ddog.persistence.rdb.jpa.repository;


import ddog.persistence.rdb.jpa.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
    Optional<PaymentJpaEntity> findByPaymentId(Long paymentId);
    Optional<PaymentJpaEntity> findByIdempotencyKey(String idempotencyKey);
    Optional<PaymentJpaEntity> findByPaymentUid(String paymentUid);
    Optional<List<PaymentJpaEntity>> findByPayerId(Long payerId);
}
