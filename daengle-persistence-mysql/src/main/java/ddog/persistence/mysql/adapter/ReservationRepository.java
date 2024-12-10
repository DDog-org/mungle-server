package ddog.persistence.mysql.adapter;

import ddog.domain.payment.Reservation;
import ddog.persistence.mysql.jpa.entity.ReservationJpaEntity;
import ddog.persistence.mysql.jpa.repository.ReservationJpaRepository;
import ddog.domain.payment.port.ReservationPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPersist {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationJpaEntity.from(reservation))
                .toModel();
    }

    @Override
    public Optional<Reservation> findByReservationId(Long reservationId) {
        return reservationJpaRepository.findByReservationId(reservationId).map(ReservationJpaEntity::toModel);
    }
}
