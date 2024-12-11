package ddog.domain.payment.port;

import ddog.domain.payment.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReservationPersist {
    Reservation save(Reservation reservation);
    Optional<Reservation> findByReservationId(Long reservationId);

    Page<Reservation> findGroomingPaymentHistory(Long accountId, Pageable pageable);
}
