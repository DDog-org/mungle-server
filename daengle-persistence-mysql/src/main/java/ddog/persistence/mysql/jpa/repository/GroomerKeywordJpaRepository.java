package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomerKeywordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroomerKeywordJpaRepository extends JpaRepository<GroomerKeywordJpaEntity, Long> {

    GroomerKeywordJpaEntity save(GroomerKeywordJpaEntity groomerKeyword);

}
