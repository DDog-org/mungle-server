package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.VetDaengleMeterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetDaengleMeterJpaRepository extends JpaRepository<VetDaengleMeterJpaEntity, Long> {
    VetDaengleMeterJpaEntity save(VetDaengleMeterJpaEntity vetDaengleMeter);
}
