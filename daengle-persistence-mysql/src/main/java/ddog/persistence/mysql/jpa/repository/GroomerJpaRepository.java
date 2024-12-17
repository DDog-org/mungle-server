package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomerJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroomerJpaRepository extends JpaRepository<GroomerJpaEntity, Long> {

    Optional<GroomerJpaEntity> findByAccountId(Long accountId);

    Optional<GroomerJpaEntity> findByGroomerId(Long groomerId);

    @Query("SELECT v FROM Groomers v " +
            "JOIN v.keywords k " +
            "WHERE (:region IS NULL OR v.address LIKE %:region%) " +
            "AND (:keyword IS NULL OR v.name LIKE %:keyword%) " +
            "AND (:tag IS NULL OR k = :tag)")
    Page<GroomerJpaEntity> findAllGroomersBy(
            @Param("region") String region,
            @Param("keyword") String keyword,
            @Param("tag") String tag,
            Pageable pageable
    );
}
