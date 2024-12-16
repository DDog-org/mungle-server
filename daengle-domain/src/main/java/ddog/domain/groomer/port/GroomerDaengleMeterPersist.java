package ddog.domain.groomer.port;

import ddog.domain.groomer.GroomerDaengleMeter;

import java.util.Optional;

public interface GroomerDaengleMeterPersist {
    GroomerDaengleMeter save(GroomerDaengleMeter groomerDaengleMeter);
    Optional<GroomerDaengleMeter> findByGroomerId(Long groomerId);
}
