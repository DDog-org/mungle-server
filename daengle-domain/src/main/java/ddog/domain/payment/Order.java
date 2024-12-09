package ddog.domain.payment;

import ddog.domain.payment.dto.PostOrderInfo;
import ddog.domain.payment.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long orderId;
    private ServiceType serviceType;
    private Long price;
    private Long estimateId;
    private String orderUid;
    private Long accountId;
    private String customerName;
    private Long recipientId;
    private String recipientName;
    private String shopName;
    private LocalDateTime orderDate;
    private LocalDateTime schedule;
    private String visitorName;
    private String customerPhoneNumber;
    private String visitorPhoneNumber;
    private Payment payment;

    public static Order createBy(Long accountId, PostOrderInfo postOrderInfo, Payment payment) {
        return Order.builder()
                .serviceType(postOrderInfo.getServiceType())
                .price(postOrderInfo.getPrice())
                .estimateId(postOrderInfo.getEstimateId())
                .orderUid(String.valueOf(UUID.randomUUID()))
                .accountId(accountId)
                .customerName(postOrderInfo.getCustomerName())
                .recipientId(postOrderInfo.getRecipientId())
                .recipientName(postOrderInfo.getRecipientName())
                .shopName(postOrderInfo.getShopName())
                .orderDate(LocalDateTime.now())
                .schedule(postOrderInfo.getSchedule())
                .visitorName(postOrderInfo.getVisitorName())
                .customerPhoneNumber(postOrderInfo.getCustomerPhoneNumber())
                .visitorPhoneNumber(postOrderInfo.getVisitorPhoneNumber())
                .payment(payment)
                .build();
    }
}
