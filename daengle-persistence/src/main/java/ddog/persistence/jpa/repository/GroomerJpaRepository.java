package ddog.persistence.jpa.repository;

import ddog.daengleserver.infrastructure.po.GroomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroomerJpaRepository extends JpaRepository<GroomerJpaEntity, Long> {

    Optional<GroomerJpaEntity> findByAccountId(Long accountId);

    GroomerJpaEntity getByAccountId(Long accountId);
}
