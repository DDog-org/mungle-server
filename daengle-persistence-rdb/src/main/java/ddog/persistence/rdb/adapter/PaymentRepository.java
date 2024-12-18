package ddog.persistence.rdb.adapter;


import ddog.domain.payment.Payment;
import ddog.persistence.rdb.jpa.entity.PaymentJpaEntity;
import ddog.persistence.rdb.jpa.repository.PaymentJpaRepository;
import ddog.domain.payment.port.PaymentPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    @RequiredArgsConstructor
    public class PaymentRepository implements PaymentPersist {

        private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Optional<Payment> findByPaymentId(Long paymentId) {
        return paymentJpaRepository.findByPaymentId(paymentId).map(PaymentJpaEntity::toModel);
    }

    @Override
    public Optional<List<Payment>> findByPayerId(Long paymentId) {
        return paymentJpaRepository.findByPayerId(paymentId)
                .map(paymentJpaEntities -> paymentJpaEntities.stream()
                        .map(PaymentJpaEntity::toModel)
                        .toList());
    }

    @Override
        public Payment save(Payment payment) {
            PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.save(PaymentJpaEntity.from(payment));
            return paymentJpaEntity.toModel();
        }

    @Override
    public Optional<Payment> findByIdempotencyKey(String idempotencyKey) {
        return paymentJpaRepository.findByIdempotencyKey(idempotencyKey).map(PaymentJpaEntity::toModel);
    }
}
