package ddog.domain.vet.port;

import ddog.domain.vet.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VetPersist {
    Optional<Vet> findByAccountId(Long accountId);
    Optional<Vet> findByVetId(Long vetId);

    Vet save(Vet vet);

    Page<Vet> findByAddress(String address, Pageable pageable);
    List<Vet> findByAddressPrefix(String addressPrefix);

    Page<Vet> findVetByKeyword(String address, String keyword, String tag, Pageable pageable);
}
