package ddog.groomer.application;

import ddog.domain.groomer.Groomer;

public interface GroomerRepository {

    Groomer getGroomerByAccountId(Long accountId);
}
