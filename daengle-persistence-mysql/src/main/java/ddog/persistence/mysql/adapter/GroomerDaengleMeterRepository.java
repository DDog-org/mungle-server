package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.GroomerDaengleMeter;
import ddog.domain.groomer.port.GroomerDaengleMeterPersist;
import ddog.persistence.mysql.jpa.entity.GroomerDanegleMeterJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomerDaengleMeterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomerDaengleMeterRepository implements GroomerDaengleMeterPersist {

    private final GroomerDaengleMeterJpaRepository groomerDaengleMeterJpaRepository;

    @Override
    public GroomerDaengleMeter save(GroomerDaengleMeter groomerDaengleMeter) {
        return groomerDaengleMeterJpaRepository.save(GroomerDanegleMeterJpaEntity.from(groomerDaengleMeter)).toModel();
    }
}
