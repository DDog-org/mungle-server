package ddog.persistence.mysql.adapter;

import ddog.domain.vet.VetDaengleMeter;
import ddog.domain.vet.port.VetDaengleMeterPersist;
import ddog.persistence.mysql.jpa.entity.VetDaengleMeterJpaEntity;
import ddog.persistence.mysql.jpa.repository.VetDaengleMeterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VetDaengleMeterRepository implements VetDaengleMeterPersist {

    private final VetDaengleMeterJpaRepository vetDaengleMeterJpaRepository;

    @Override
    public VetDaengleMeter save(VetDaengleMeter vetDaengleMeter) {
        return vetDaengleMeterJpaRepository.save(VetDaengleMeterJpaEntity.from(vetDaengleMeter)).toModel();
    }
}
