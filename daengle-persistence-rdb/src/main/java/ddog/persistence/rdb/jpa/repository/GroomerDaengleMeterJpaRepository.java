package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.GroomerDanegleMeterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroomerDaengleMeterJpaRepository extends JpaRepository<GroomerDanegleMeterJpaEntity, Long> {
    Optional<GroomerDanegleMeterJpaEntity> findByGroomerId(Long groomerId);
    GroomerDanegleMeterJpaEntity save(GroomerDanegleMeterJpaEntity groomerDanegleMeter);
}
