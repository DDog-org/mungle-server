package ddog.domain.payment.port;

import ddog.domain.payment.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentPersist {
    Optional<Payment> findByPaymentId(Long paymentId);
    Optional<List<Payment>> findByPayerId(Long paymentId);
    Payment save(Payment payment);
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}
