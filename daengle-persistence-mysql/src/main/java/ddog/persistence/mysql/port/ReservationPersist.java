package ddog.persistence.mysql.port;

import ddog.domain.payment.Reservation;

public interface ReservationPersist {
    Reservation save(Reservation reservation);
}
