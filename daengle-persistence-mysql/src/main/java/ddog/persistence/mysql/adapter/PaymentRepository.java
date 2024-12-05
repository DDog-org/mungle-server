package ddog.persistence.mysql.adapter;


import ddog.domain.payment.Payment;
import ddog.persistence.mysql.jpa.entity.PaymentJpaEntity;
import ddog.persistence.mysql.jpa.repository.PaymentJpaRepository;
import ddog.persistence.mysql.port.PaymentPersist;
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
