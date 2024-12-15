package ddog.payment.application.mapper;

import ddog.domain.payment.Order;
import ddog.domain.payment.Payment;
import ddog.domain.user.User;
import ddog.payment.presentation.dto.PostOrderInfo;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderMapper {

    public static Order createBy(User user, PostOrderInfo postOrderInfo, Payment payment) {
        return Order.builder()
                .serviceType(postOrderInfo.getServiceType())
                .petId(postOrderInfo.getPetId())
                .price(postOrderInfo.getPrice())
                .estimateId(postOrderInfo.getEstimateId())
                .orderUid(String.valueOf(UUID.randomUUID()))
                .accountId(user.getAccountId())
                .customerName(user.getNickname())
                .recipientId(postOrderInfo.getRecipientId())
                .recipientImageUrl(postOrderInfo.getRecipientImageUrl())
                .recipientName(postOrderInfo.getRecipientName())
                .shopName(postOrderInfo.getShopName())
                .orderDate(LocalDateTime.now())
                .schedule(postOrderInfo.getSchedule())
                .visitorName(postOrderInfo.getVisitorName())
                .customerPhoneNumber(user.getPhoneNumber())
                .visitorPhoneNumber(postOrderInfo.getVisitorPhoneNumber())
                .payment(payment)
                .build();
    }
}
