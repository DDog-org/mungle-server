package ddog.daengleserver.infrastructure;

import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.infrastructure.po.PaymentJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

    @Repository
    @RequiredArgsConstructor
    public class PaymentRepositoryImpl implements PaymentRepository {

        private final PaymentJpaRepository paymentJpaRepository;

        @Override
        public void save(Payment payment) {
            PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.save(PaymentJpaEntity.fromModel(payment));
            payment.updatePaymentId(paymentJpaEntity.getPaymentId());
        }
}
