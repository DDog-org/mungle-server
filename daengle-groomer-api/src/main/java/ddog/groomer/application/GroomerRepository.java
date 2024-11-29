package ddog.groomer.application;

import ddog.daengleserver.domain.account.Groomer;

public interface GroomerRepository {

    String getAddressById(Long accountId);

    Groomer getGroomerByAccountId(Long accountId);
}
