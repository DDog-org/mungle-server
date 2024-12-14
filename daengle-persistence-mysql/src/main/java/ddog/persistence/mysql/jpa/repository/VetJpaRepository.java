package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.VetJpaEntity;
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
            "WHERE REPLACE(v.address, ' ', '') = REPLACE(:address, ' ', '')")
    Page<VetJpaEntity> findVetsByAddress(@Param("address") String address, Pageable pageable);

    @Query("SELECT v FROM Vets v WHERE v.address LIKE CONCAT(:addressPrefix, '%')")
    List<VetJpaEntity> findVetsByAddressPrefix(@Param("addressPrefix") String addressPrefix);

}
