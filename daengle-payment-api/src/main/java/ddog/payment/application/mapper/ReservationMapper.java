package ddog.payment.application.mapper;

import ddog.domain.payment.Order;
import ddog.domain.payment.Payment;
import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;

public class ReservationMapper {

    public static Reservation createBy(Order order, Payment payment) {
        return Reservation.builder()
                .estimateId(order.getEstimateId())
                .serviceType(order.getServiceType())
                .reservationStatus(ReservationStatus.DEPOSIT_PAID)
                .recipientId(order.getRecipientId())  //수의사 or 병원 PK
                .recipientImageUrl(order.getRecipientImageUrl())
                .recipientName(order.getRecipientName())
                .shopName(order.getShopName())
                .schedule(order.getSchedule())
                .deposit(order.getPrice())
                .customerId(order.getAccountId())
                .customerPhoneNumber(order.getCustomerPhoneNumber())
                .visitorName(order.getVisitorName())
                .visitorPhoneNumber(order.getVisitorPhoneNumber())
                .paymentId(payment.getPaymentId())
                .build();
    }
}
