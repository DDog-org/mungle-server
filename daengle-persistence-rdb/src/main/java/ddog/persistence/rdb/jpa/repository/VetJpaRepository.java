package ddog.persistence.rdb.jpa.repository;

import ddog.domain.vet.enums.CareBadge;
import ddog.persistence.rdb.jpa.entity.VetJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VetJpaRepository extends JpaRepository<VetJpaEntity, Long> {

    Optional<VetJpaEntity> findByAccountId(Long accountId);
    Optional<VetJpaEntity> findByVetId(Long vetId);

    @Query("SELECT v FROM Vets v " +
            "WHERE REPLACE(v.address, ' ', '') LIKE CONCAT('%', REPLACE(:address, ' ', ''), '%')")
    Page<VetJpaEntity> findVetsByAddress(@Param("address") String address, Pageable pageable);

    @Query("SELECT v FROM Vets v WHERE v.address LIKE CONCAT(:addressPrefix, '%')")
    List<VetJpaEntity> findVetsByAddressPrefix(@Param("addressPrefix") String addressPrefix);

    @Query("SELECT v FROM Vets v " +
            "LEFT JOIN v.keywords k " +
            "WHERE (:address IS NULL OR :address = '' OR v.address LIKE CONCAT('%', :address, '%')) " +
            "AND (:keyword IS NULL OR :keyword = '' OR v.name LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:badge IS NULL OR :badge = '' OR :badge MEMBER OF v.badges)")
    Page<VetJpaEntity> findAllVetsBy(
            @Param("address") String address,
            @Param("keyword") String keyword,
            @Param("badge") CareBadge badge,
            Pageable pageable
    );

    void deleteByAccountId(Long accountId);
}
