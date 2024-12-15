package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomerDanegleMeterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomerDaengleMeterJpaRepository extends JpaRepository<GroomerDanegleMeterJpaEntity, Long> {
    GroomerDanegleMeterJpaEntity save(GroomerDanegleMeterJpaEntity groomerDanegleMeter);
}
