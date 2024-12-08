package ddog.persistence.mysql.jpa.entity;

import ddog.domain.payment.Reservation;
import ddog.domain.payment.enums.ReservationStatus;
import ddog.domain.payment.enums.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Reservations")
public class ReservationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    private Long estimateId;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Long recipientId;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
    private Long deposit;
    private Long customerId;
    private String customerPhoneNumber;
    private String visitorName;
    private String visitorPhoneNumber;
    private Long paymentId;

    public static ReservationJpaEntity from(Reservation reservation) {
        return ReservationJpaEntity.builder()
                .reservationId(reservation.getReservationId())
                .estimateId(reservation.getEstimateId())
                .serviceType(reservation.getServiceType())
                .reservationStatus(reservation.getReservationStatus())
                .recipientId(reservation.getRecipientId())
                .recipientName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .schedule(reservation.getSchedule())
                .deposit(reservation.getDeposit())
                .customerId(reservation.getCustomerId())
                .customerPhoneNumber(reservation.getCustomerPhoneNumber())
                .visitorName(reservation.getVisitorName())
                .visitorPhoneNumber(reservation.getVisitorPhoneNumber())
                .paymentId(reservation.getPaymentId())
                .build();
    }

    public Reservation toModel() {
        return Reservation.builder()
                .reservationId(reservationId)
                .estimateId(estimateId)
                .serviceType(serviceType)
                .reservationStatus(reservationStatus)
                .recipientId(recipientId)
                .recipientName(recipientName)
                .shopName(shopName)
                .schedule(schedule)
                .deposit(deposit)
                .customerId(customerId)
                .customerPhoneNumber(customerPhoneNumber)
                .visitorName(visitorName)
                .visitorPhoneNumber(visitorPhoneNumber)
                .paymentId(paymentId)
                .build();
    }
}