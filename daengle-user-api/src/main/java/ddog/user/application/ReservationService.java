package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.user.application.exception.ReservationException;
import ddog.user.application.exception.ReservationExceptionType;
import ddog.user.presentation.reservation.dto.ReservationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationPersist reservationPersist;

    public ReservationSummary getReservationSummary(Long reservationId) {
        Reservation reservation = reservationPersist.findBy(reservationId).orElseThrow(()
                -> new ReservationException(ReservationExceptionType.RESERVATION_NOT_FOUND));

        return ReservationSummary.builder()
                .reservationId(reservation.getReservationId())
                .recipientName(reservation.getRecipientName())
                .shopName(reservation.getShopName())
                .schedule(reservation.getSchedule())
                .build();
    }
}
