package ddog.persistence.mysql.port;

import ddog.domain.groomer.Groomer;

import java.util.Optional;

public interface GroomerPersist {

    Optional<Groomer> findByAccountId(Long accountId);

    Optional<Groomer> findBy(Long id);

    void save(Groomer newGroomer);
}
