package ddog.persistence.rdb.adapter;

import ddog.domain.vet.VetDaengleMeter;
import ddog.domain.vet.port.VetDaengleMeterPersist;
import ddog.persistence.rdb.jpa.entity.VetDaengleMeterJpaEntity;
import ddog.persistence.rdb.jpa.repository.VetDaengleMeterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VetDaengleMeterRepository implements VetDaengleMeterPersist {

    private final VetDaengleMeterJpaRepository vetDaengleMeterJpaRepository;

    @Override
    public Optional<VetDaengleMeter> findByVetId(Long vetId) {
        return vetDaengleMeterJpaRepository.findByVetId(vetId).map(VetDaengleMeterJpaEntity::toModel);
    }

    @Override
    public VetDaengleMeter save(VetDaengleMeter vetDaengleMeter) {
        return vetDaengleMeterJpaRepository.save(VetDaengleMeterJpaEntity.from(vetDaengleMeter)).toModel();
    }
}
