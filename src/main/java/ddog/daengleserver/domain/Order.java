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
    private Long accountId;
    private Payment payment;

    public static Order createBy(Long accountId, PostOrderReq postOrderReq, Payment payment) {
        return Order.builder()
                .orderId(null)
                .price(postOrderReq.getPrice())
                .itemId(postOrderReq.getItemId())
                .orderUid(String.valueOf(UUID.randomUUID()))
                .accountId(accountId)
                .payment(payment)
                .build();
    }
}
