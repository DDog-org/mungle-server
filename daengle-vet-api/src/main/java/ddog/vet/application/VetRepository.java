package ddog.vet.application;

import ddog.daengleserver.domain.account.Vet;

public interface VetRepository {

    String getAddressById(Long accountId);

    Vet getVetByAccountId(Long accountId);

}
