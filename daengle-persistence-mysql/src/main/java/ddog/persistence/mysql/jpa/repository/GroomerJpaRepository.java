package ddog.persistence.mysql.jpa.repository;

import ddog.domain.groomer.enums.GroomingKeyword;
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
            "LEFT JOIN v.keywords k " +
            "WHERE (:address IS NULL OR :address = '' OR v.address LIKE CONCAT('%', :address, '%')) " +
            "AND (:keyword IS NULL OR :keyword = '' OR v.name LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:tag IS NULL OR :tag = '' OR v.name LIKE CONCAT('%', :tag, '%'))")
    Page<GroomerJpaEntity> findAllGroomersBy(
            @Param("address") String address,
            @Param("keyword") String keyword,
            @Param("tag") GroomingKeyword tag,
            Pageable pageable
    );
}
