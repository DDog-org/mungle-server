package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.po.PetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

    List<PetJpaEntity> findByUserId(Long userId);

    void deleteByPetId(Long petId);

}
