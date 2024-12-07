package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.ReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    Optional<ReservationJpaEntity> findByReservationId(Long id);
}
