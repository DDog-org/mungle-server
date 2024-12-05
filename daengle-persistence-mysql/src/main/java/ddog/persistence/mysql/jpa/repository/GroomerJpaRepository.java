package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroomerJpaRepository extends JpaRepository<GroomerJpaEntity, Long> {

    Optional<GroomerJpaEntity> findByAccountId(Long accountId);

    GroomerJpaEntity getByAccountId(Long accountId);
}
