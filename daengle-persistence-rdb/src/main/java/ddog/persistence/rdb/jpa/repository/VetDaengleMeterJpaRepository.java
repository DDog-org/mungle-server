package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.VetDaengleMeterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetDaengleMeterJpaRepository extends JpaRepository<VetDaengleMeterJpaEntity, Long> {
    Optional<VetDaengleMeterJpaEntity> findByVetId(Long vetId);
    VetDaengleMeterJpaEntity save(VetDaengleMeterJpaEntity vetDaengleMeter);
}
