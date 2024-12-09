package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.Groomer;
import ddog.persistence.mysql.jpa.entity.GroomerJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomerJpaRepository;
import ddog.persistence.mysql.port.GroomerPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroomerRepository implements GroomerPersist {

    private final GroomerJpaRepository groomerJpaRepository;

    @Override
    public Optional<Groomer> findByAccountId(Long accountId) {
        return groomerJpaRepository.findByAccountId(accountId)
                .map(GroomerJpaEntity::toModel);
    }

    @Override
    public Optional<Groomer> findBy(Long accountId) {
        return groomerJpaRepository.findByAccountId(accountId).map(GroomerJpaEntity::toModel);
    }

    @Override
    public void save(Groomer newGroomer) {
        groomerJpaRepository.save(GroomerJpaEntity.from(newGroomer));
    }
}
