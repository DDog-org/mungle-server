package ddog.persistence.adapter;

import ddog.domain.vet.Vet;
import ddog.persistence.jpa.repository.VetJpaRepository;
import ddog.persistence.port.VetPersist;
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
}
