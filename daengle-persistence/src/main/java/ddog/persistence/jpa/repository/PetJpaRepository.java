package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.PetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

    Optional<PetJpaEntity> findPetsByAccountId(Long accountId);

    void deleteByPetId(Long petId);

}
