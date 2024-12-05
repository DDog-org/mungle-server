package ddog.persistence.mysql.adapter;

import ddog.domain.vet.Vet;
import ddog.persistence.mysql.jpa.entity.VetJpaEntity;
import ddog.persistence.mysql.jpa.repository.VetJpaRepository;
import ddog.persistence.mysql.port.VetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VetRepository implements VetPersist {

    private final VetJpaRepository vetJpaRepository;

    @Override
    public Vet getVetByAccountId(Long accountId) {
        return vetJpaRepository.getByAccountId(accountId)
                .toModel();
    }

    @Override
    public void save(Vet vet) {
        vetJpaRepository.save(VetJpaEntity.from(vet));
    }
}
