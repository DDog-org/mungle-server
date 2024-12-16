package ddog.persistence.mysql.jpa.entity;

import ddog.domain.payment.Payment;
import ddog.domain.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payments")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "paymentUid"))
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long payerId;
    private Long price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime paymentDate;

    @Column(nullable = false, unique = true)
    private String paymentUid;

    public Payment toModel() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .payerId(this.payerId)
                .price(this.price)
                .status(this.status)
                .paymentDate(this.paymentDate)
                .paymentUid(this.paymentUid)
                .build();
    }

    public static PaymentJpaEntity from(Payment payment) {
        return PaymentJpaEntity.builder()
                .paymentId(payment.getPaymentId())
                .payerId(payment.getPayerId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .paymentUid(payment.getPaymentUid())
                .build();
    }
}
