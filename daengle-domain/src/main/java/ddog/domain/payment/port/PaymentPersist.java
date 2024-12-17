package ddog.domain.payment.port;

import ddog.domain.payment.Payment;

import java.util.Optional;

public interface PaymentPersist {
    Optional<Payment> findByPaymentId(Long paymentId);
    Payment save(Payment payment);
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}
