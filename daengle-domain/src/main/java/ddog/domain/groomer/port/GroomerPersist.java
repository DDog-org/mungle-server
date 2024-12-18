package ddog.domain.groomer.port;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GroomerPersist {

    Optional<Groomer> findByAccountId(Long accountId);

    Groomer save(Groomer newGroomer);

    Optional<Groomer> findByGroomerId(Long groomerId);

    Page<Groomer> findGroomerByKeyword(String address, String keyword, GroomingKeyword tag, Pageable pageable);
    void deleteByAccountId(Long accountId);
}
