package ddog.persistence.mysql.port;

import ddog.domain.payment.Payment;

public interface PaymentPersist {
    Payment save(Payment payment);
}
