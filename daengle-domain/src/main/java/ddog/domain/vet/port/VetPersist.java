package ddog.domain.vet.port;

import ddog.domain.vet.Vet;

import java.util.Optional;

public interface VetPersist {
    Optional<Vet> findByAccountId(Long accountId);

    void save(Vet vet);
}
