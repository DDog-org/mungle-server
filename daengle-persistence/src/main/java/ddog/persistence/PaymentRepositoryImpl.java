package ddog.persistence;

import ddog.daengleserver.application.repository.PaymentRepository;
import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.infrastructure.po.PaymentJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

    @Repository     //TODO impl 접미사 제거하기
    @RequiredArgsConstructor
    public class PaymentRepositoryImpl implements PaymentRepository {

        private final PaymentJpaRepository paymentJpaRepository;

        @Override
        public Payment save(Payment payment) {
            PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.save(PaymentJpaEntity.fromModel(payment));
            return paymentJpaEntity.toModel();
        }
}
