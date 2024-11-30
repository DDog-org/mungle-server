package ddog.persistence.adapter;

import ddog.domain.groomer.Groomer;
import ddog.persistence.jpa.repository.GroomerJpaRepository;
import ddog.persistence.port.GroomerPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomerRepository implements GroomerPersist {

    private final GroomerJpaRepository groomerJpaRepository;

    @Override
    public Groomer getGroomerByAccountId(Long accountId) {
        return groomerJpaRepository.getByAccountId(accountId)
                .toModel();
    }
}
