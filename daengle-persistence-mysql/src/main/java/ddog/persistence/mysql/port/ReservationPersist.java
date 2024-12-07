package ddog.persistence.mysql.port;

import ddog.domain.payment.Reservation;

import java.util.Optional;

public interface ReservationPersist {
    Reservation save(Reservation reservation);
    Optional<Reservation> findBy(Long reservationId);
}
