package ddog.persistence.mysql.port;

import ddog.domain.vet.Vet;

public interface VetPersist {
    Vet getVetByAccountId(Long accountId);
    void save(Vet vet);
}
