package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Payment;
import ddog.daengleserver.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payments")
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long price;
    private PaymentStatus status;
    private String paymentUid;

    public Payment toModel() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .price(this.price)
                .status(this.status)
                .paymentUid(this.paymentUid)
                .build();
    }

    public static PaymentJpaEntity fromModel(Payment payment) {
        return PaymentJpaEntity.builder()
                .paymentId(payment.getPaymentId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .paymentUid(payment.getPaymentUid())
                .build();
    }

}
