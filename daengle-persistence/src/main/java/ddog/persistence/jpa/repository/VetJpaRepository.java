package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.VetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetJpaRepository extends JpaRepository<VetJpaEntity, Long> {

    Optional<VetJpaEntity> findByAccountId(Long accountId);

    VetJpaEntity getByAccountId(Long accountId);
}
