package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.GroomerDaengleMeter;
import ddog.domain.groomer.port.GroomerDaengleMeterPersist;
import ddog.persistence.mysql.jpa.entity.GroomerDanegleMeterJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomerDaengleMeterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroomerDaengleMeterRepository implements GroomerDaengleMeterPersist {

    private final GroomerDaengleMeterJpaRepository groomerDaengleMeterJpaRepository;

    @Override
    public Optional<GroomerDaengleMeter> findByGroomerId(Long groomerId) {
        return groomerDaengleMeterJpaRepository.findByGroomerId(groomerId).map(GroomerDanegleMeterJpaEntity::toModel);
    }
    
    @Override
    public GroomerDaengleMeter save(GroomerDaengleMeter groomerDaengleMeter) {
        return groomerDaengleMeterJpaRepository.save(GroomerDanegleMeterJpaEntity.from(groomerDaengleMeter)).toModel();
    }
}
