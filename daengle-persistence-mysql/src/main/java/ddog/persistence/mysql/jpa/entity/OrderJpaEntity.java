package ddog.persistence.mysql.jpa.entity;

import ddog.domain.payment.Order;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.Payment;
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
@Entity(name = "Orders")
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    private Long price;
    private Long estimateId;
    private String orderUid;
    private Long accountId;
    private String customerName;
    private String recipientName;
    private String shopName;
    private LocalDateTime orderDate;
    private LocalDateTime schedule;
    private String visitorName;
    private String customerPhoneNumber;
    private String visitorPhoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    public Order toModel() {
        return Order.builder()
                .orderId(this.orderId)
                .serviceType(this.serviceType)
                .price(this.price)
                .estimateId(this.estimateId)
                .orderUid(this.orderUid)
                .accountId(this.accountId)
                .customerName(this.customerName)
                .recipientName(this.recipientName)
                .shopName(this.shopName)
                .orderDate(this.orderDate)
                .schedule(this.schedule)
                .visitorName(this.visitorName)
                .customerPhoneNumber(this.customerPhoneNumber)
                .visitorPhoneNumber(this.visitorPhoneNumber)
                .payment(this.payment.toModel())
                .build();
    }

    public static OrderJpaEntity fromModel(Order order) {
        Payment payment = order.getPayment();

        return OrderJpaEntity.builder()
                .orderId(order.getOrderId())
                .serviceType(order.getServiceType())
                .price(order.getPrice())
                .estimateId(order.getEstimateId())
                .orderUid(order.getOrderUid())
                .accountId(order.getAccountId())
                .customerName(order.getCustomerName())
                .recipientName(order.getRecipientName())
                .shopName(order.getShopName())
                .orderDate(order.getOrderDate())
                .schedule(order.getSchedule())
                .visitorName(order.getVisitorName())
                .customerPhoneNumber(order.getCustomerPhoneNumber())
                .visitorPhoneNumber(order.getVisitorPhoneNumber())
                .payment(PaymentJpaEntity.fromModel(payment))
                .build();
    }
}
