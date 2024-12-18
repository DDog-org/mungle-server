package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.GroomerKeywordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomerKeywordJpaRepository extends JpaRepository<GroomerKeywordJpaEntity, Long> {

    GroomerKeywordJpaEntity save(GroomerKeywordJpaEntity groomerKeyword);

}
