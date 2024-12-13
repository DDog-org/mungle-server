package ddog.domain.vet.port;

import ddog.domain.vet.Vet;

import java.util.List;
import java.util.Optional;

public interface VetPersist {
    Optional<Vet> findByAccountId(Long accountId);
    Optional<Vet> findByVetId(Long vetId);

    void save(Vet vet);

    List<Vet> findByAddress(String address);
    List<Vet> findByAddressPrefix(String addressPrefix);
}
