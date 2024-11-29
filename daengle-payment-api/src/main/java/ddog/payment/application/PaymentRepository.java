package ddog.payment.application;

import ddog.daengleserver.domain.Payment;
import ddog.payment.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
