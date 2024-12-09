package ddog.user.application;

import ddog.domain.payment.Reservation;
import ddog.persistence.mysql.port.ReservationPersist;
import ddog.user.application.exception.estimate.ReservationException;
import ddog.user.application.exception.estimate.ReservationExceptionType;
import ddog.user.presentation.reservation.dto.ReservationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationPersist reservationPersist;

    @Transactional(readOnly = true)
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
