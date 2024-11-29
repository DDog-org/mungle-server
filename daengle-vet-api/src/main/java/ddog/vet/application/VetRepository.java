package ddog.vet.application;

import ddog.domain.vet.Vet;

public interface VetRepository {

    Vet getVetByAccountId(Long accountId);

}
