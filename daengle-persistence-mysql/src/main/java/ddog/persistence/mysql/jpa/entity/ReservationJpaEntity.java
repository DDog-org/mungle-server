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
    private Long petId;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Long recipientId;
    private String recipientImageUrl;
    private String recipientName;
    private String shopName;
    private LocalDateTime schedule;
    private Long deposit;
    private Long customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String visitorName;
    private String visitorPhoneNumber;
    private Long paymentId;
    private Long petId;

    public static ReservationJpaEntity from(Reservation reservation) {
        return ReservationJpaEntity.builder()
                .reservationId(reservation.getReservationId())
                .estimateId(reservation.getEstimateId())
                .petId(reservation.getPetId())
                .serviceType(reservation.getServiceType())
                .reservationStatus(reservation.getReservationStatus())
                .recipientId(reservation.getRecipientId())
                .recipientImageUrl(reservation.getRecipientImageUrl())
                .recipientName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .schedule(reservation.getSchedule())
                .deposit(reservation.getDeposit())
                .customerId(reservation.getCustomerId())
                .customerName(reservation.getCustomerName())
                .customerPhoneNumber(reservation.getCustomerPhoneNumber())
                .visitorName(reservation.getVisitorName())
                .visitorPhoneNumber(reservation.getVisitorPhoneNumber())
                .paymentId(reservation.getPaymentId())
                .petId(reservation.getPetId())
                .build();
    }

    public Reservation toModel() {
        return Reservation.builder()
                .reservationId(reservationId)
                .estimateId(estimateId)
                .petId(petId)
                .serviceType(serviceType)
                .reservationStatus(reservationStatus)
                .recipientId(recipientId)
                .recipientImageUrl(recipientImageUrl)
                .recipientName(recipientName)
                .shopName(shopName)
                .schedule(schedule)
                .deposit(deposit)
                .customerId(customerId)
                .customerName(customerName)
                .customerPhoneNumber(customerPhoneNumber)
                .visitorName(visitorName)
                .visitorPhoneNumber(visitorPhoneNumber)
                .paymentId(paymentId)
                .petId(petId)
                .build();
    }
}