package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
