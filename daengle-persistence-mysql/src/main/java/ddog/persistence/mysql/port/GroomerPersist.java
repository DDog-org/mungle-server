package ddog.persistence.mysql.port;

import ddog.domain.groomer.Groomer;

import java.util.Optional;

public interface GroomerPersist {
    Groomer getGroomerByAccountId(Long accountId);

    Optional<Groomer> findBy(Long id);

    void save(Groomer newGroomer);
}
