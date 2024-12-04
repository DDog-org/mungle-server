package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.PetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

    Optional<PetJpaEntity> findPetByPetId(Long petId);

    void deleteByPetId(Long petId);

}
