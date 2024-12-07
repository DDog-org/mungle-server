package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CareEstimateJpaRepository extends JpaRepository<CareEstimateJpaEntity, Long> {

    CareEstimateJpaEntity save(CareEstimateJpaEntity careEstimate);

    Optional<CareEstimateJpaEntity> findByEstimateId(Long estimateId);

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.proposal = 'GENERAL' AND (c.status = 'NEW' OR c.status = 'PENDING') AND c.address = :address")
    List<CareEstimateJpaEntity> findCareEstimatesByAddress(@Param("address") String address);

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.proposal = 'DESIGNATION' AND (c.status = 'NEW' OR c.status = 'PENDING') AND c.vetId = :vetId")
    List<CareEstimateJpaEntity> findCareEstimatesByVetId(@Param("vetId") Long vetId);

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.status = 'PENDING' AND c.petId = :petId")
    List<CareEstimateJpaEntity> findCareEstimatesByPetId(@Param("petId") Long petId);
}
