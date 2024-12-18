package ddog.persistence.mysql.adapter;

import ddog.domain.groomer.GroomerKeyword;
import ddog.domain.groomer.port.GroomerKeywordPersist;
import ddog.persistence.mysql.jpa.entity.GroomerKeywordJpaEntity;
import ddog.persistence.mysql.jpa.repository.GroomerKeywordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroomerKeywordRepository implements GroomerKeywordPersist {

    private final GroomerKeywordJpaRepository groomerKeywordJpaRepository;

    @Override
    public GroomerKeyword save(GroomerKeyword keyword) {
        return groomerKeywordJpaRepository.save(GroomerKeywordJpaEntity.from(keyword))
                .toModel();
    }
}
