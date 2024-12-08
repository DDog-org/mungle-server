package ddog.persistence.mysql.port;

import ddog.domain.vet.Vet;

import java.util.Optional;

public interface VetPersist {
    Vet getVetByAccountId(Long accountId);
    void save(Vet vet);
    Optional<Vet> findBy(Long id);
}
