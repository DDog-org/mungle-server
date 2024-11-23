package ddog.daengleserver.domain;

import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long orderId;
    private Long price;
    private Long itemId;
    private String orderUid;
    private Long userId;
    private Long paymentId;

    public static Order createBy(PostOrderReq postOrderReq, Payment payment) {
        return Order.builder()
                .orderId(null)
                .price(postOrderReq.getPrice())
                .itemId(postOrderReq.getItemId())
                .orderUid(String.valueOf(UUID.randomUUID()))
                .userId(postOrderReq.getUserId())
                .paymentId(payment.getPaymentId())
                .build();
    }
}
