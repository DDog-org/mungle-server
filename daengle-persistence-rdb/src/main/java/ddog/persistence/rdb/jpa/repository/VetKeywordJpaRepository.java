package ddog.persistence.rdb.jpa.repository;

import ddog.persistence.rdb.jpa.entity.VetKeywordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetKeywordJpaRepository extends JpaRepository<VetKeywordJpaEntity, Long> {

    VetKeywordJpaEntity save(VetKeywordJpaEntity vetKeyword);

}
