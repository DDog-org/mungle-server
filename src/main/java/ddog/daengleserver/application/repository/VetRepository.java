package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.account.Vet;

public interface VetRepository {

    String getAddressById(Long accountId);

    Vet getVetByAccountId(Long accountId);

}
