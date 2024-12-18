package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.VetKeywordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetKeywordJpaRepository extends JpaRepository<VetKeywordJpaEntity, Long> {

    VetKeywordJpaEntity save(VetKeywordJpaEntity vetKeyword);

}
