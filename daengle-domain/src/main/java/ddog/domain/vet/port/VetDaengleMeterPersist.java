package ddog.domain.vet.port;

import ddog.domain.vet.VetDaengleMeter;

import java.util.Optional;

public interface VetDaengleMeterPersist {
    Optional<VetDaengleMeter> findByVetId(Long vetId);
    VetDaengleMeter save(VetDaengleMeter vetDaengleMeter);
}
