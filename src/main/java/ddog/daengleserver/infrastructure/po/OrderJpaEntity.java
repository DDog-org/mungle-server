package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Order;
import ddog.daengleserver.domain.Payment;
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
    private Long userId;
    private Long paymentId;

    public Order toModel() {
        return Order.builder()
                .orderId(this.orderId)
                .price(this.price)
                .itemId(this.itemId)
                .orderUid(this.orderUid)
                .userId(this.userId)
                .paymentId(this.paymentId)
                .build();
    }

    public static OrderJpaEntity fromModel(Order order) {
        return OrderJpaEntity.builder()
                .orderId(order.getOrderId())
                .price(order.getPrice())
                .itemId(order.getItemId())
                .orderUid(order.getOrderUid())
                .userId(order.getUserId())
                .paymentId(order.getPaymentId())
                .build();
    }

}
