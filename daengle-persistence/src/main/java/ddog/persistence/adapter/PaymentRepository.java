package ddog.persistence.adapter;


import ddog.domain.payment.Payment;
import ddog.persistence.jpa.entity.PaymentJpaEntity;
import ddog.persistence.jpa.repository.PaymentJpaRepository;
import ddog.persistence.port.PaymentPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

    @Repository
    @RequiredArgsConstructor
    public class PaymentRepository implements PaymentPersist {

        private final PaymentJpaRepository paymentJpaRepository;

        @Override
        public Payment save(Payment payment) {
            PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.save(PaymentJpaEntity.fromModel(payment));
            return paymentJpaEntity.toModel();
        }
}
