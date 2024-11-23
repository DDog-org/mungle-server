package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Order toModel() {
        return Order.builder()
                .orderId(this.orderId)
                .price(this.price)
                .itemId(this.itemId)
                .orderUid(this.orderUid)
                .build();
    }

    public static OrderJpaEntity fromModel(Order order) {
        return OrderJpaEntity.builder()
                .orderId(order.getOrderId())
                .price(order.getPrice())
                .itemId(order.getItemId())
                .orderUid(order.getOrderUid())
                .build();
    }

}
