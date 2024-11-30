package ddog.persistence.jpa.entity;

import ddog.domain.order.Order;
import ddog.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long price;
    private Long itemId;
    private String orderUid;
    private Long account_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    public Order toModel() {
        return Order.builder()
                .orderId(this.orderId)
                .price(this.price)
                .itemId(this.itemId)
                .orderUid(this.orderUid)
                .accountId(this.account_id)
                .payment(this.payment.toModel())
                .build();
    }

    public static OrderJpaEntity fromModel(Order order) {
        Payment payment = order.getPayment();

        return OrderJpaEntity.builder()
                .orderId(order.getOrderId())
                .price(order.getPrice())
                .itemId(order.getItemId())
                .orderUid(order.getOrderUid())
                .account_id(order.getAccountId())
                .payment(PaymentJpaEntity.fromModel(payment))
                .build();
    }
}
