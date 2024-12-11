package ddog.persistence.mysql.adapter;

import ddog.domain.vet.Vet;
import ddog.persistence.mysql.jpa.entity.VetJpaEntity;
import ddog.persistence.mysql.jpa.repository.VetJpaRepository;
import ddog.domain.vet.port.VetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VetRepository implements VetPersist {

    private final VetJpaRepository vetJpaRepository;

    @Override
    public Optional<Vet> findByAccountId(Long accountId) {
        return vetJpaRepository.findByAccountId(accountId)
                .map(VetJpaEntity::toModel);
    }

    @Override
    public Optional<Vet> findByVetId(Long vetId) {
        return vetJpaRepository.findByVetId(vetId)
                .map(VetJpaEntity::toModel);
    }

    @Override
    public void save(Vet vet) {
        vetJpaRepository.save(VetJpaEntity.from(vet));
    }
}
