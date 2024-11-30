package ddog.persistence.port;

import ddog.domain.vet.Vet;

public interface VetPersist {
    Vet getVetByAccountId(Long accountId);
}
