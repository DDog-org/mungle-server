package ddog.vet.application;

import ddog.domain.vet.Vet;

public interface VetRepository {

    String getAddressById(Long accountId);

    Vet getVetByAccountId(Long accountId);

}
