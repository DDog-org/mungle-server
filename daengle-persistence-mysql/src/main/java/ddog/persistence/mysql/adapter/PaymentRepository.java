package ddog.persistence.mysql.adapter;


import ddog.domain.payment.Payment;
import ddog.persistence.mysql.jpa.entity.PaymentJpaEntity;
import ddog.persistence.mysql.jpa.repository.PaymentJpaRepository;
import ddog.domain.payment.port.PaymentPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
        public Payment save(Payment payment) {
            PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.save(PaymentJpaEntity.from(payment));
            return paymentJpaEntity.toModel();
        }
}
