package ddog.persistence.port;

import ddog.domain.groomer.Groomer;

public interface GroomerPersist {
    Groomer getGroomerByAccountId(Long accountId);

    void save(Groomer newGroomer);
}
