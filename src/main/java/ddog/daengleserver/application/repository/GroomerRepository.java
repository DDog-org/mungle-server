package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.account.Groomer;

public interface GroomerRepository {

    String getAddressById(Long accountId);

    Groomer getGroomerByAccountId(Long accountId);
}
