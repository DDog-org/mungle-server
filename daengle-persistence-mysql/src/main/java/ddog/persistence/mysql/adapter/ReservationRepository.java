package ddog.persistence.mysql.adapter;

import ddog.domain.payment.Reservation;
import ddog.persistence.mysql.jpa.entity.ReservationJpaEntity;
import ddog.persistence.mysql.jpa.repository.ReservationJpaRepository;
import ddog.persistence.mysql.port.ReservationPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPersist {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationJpaEntity.from(reservation))
                .toModel();
    }
}
