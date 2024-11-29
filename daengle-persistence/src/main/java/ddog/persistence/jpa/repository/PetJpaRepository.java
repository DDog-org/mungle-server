package ddog.persistence.jpa.repository;

import ddog.daengleserver.infrastructure.po.PetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

    List<PetJpaEntity> findByAccountId(Long userId);

    void deleteByPetId(Long petId);

}
