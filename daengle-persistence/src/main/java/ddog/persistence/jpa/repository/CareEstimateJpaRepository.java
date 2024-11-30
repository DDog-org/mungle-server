package ddog.persistence.jpa.repository;

import ddog.persistence.jpa.entity.CareEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CareEstimateJpaRepository extends JpaRepository<CareEstimateJpaEntity, Long> {

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.proposal = 'GENERAL' AND c.status = 'NEW' AND c.address = :address")
    List<CareEstimateJpaEntity> findGeneralCareEstimatesByAddress(@Param("address") String address);

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.proposal = 'DESIGNATION' AND c.status = 'NEW' AND c.vetId = :vetId")
    List<CareEstimateJpaEntity> findDesignationCareEstimatesByVetId(@Param("vetId") Long vetId);

    CareEstimateJpaEntity getCareEstimateJpaEntityByCareEstimateId(Long careEstimateId);

    @Query("SELECT c FROM CareEstimates c " +
            "WHERE c.status = 'PENDING' AND c.petId = :petId")
    List<CareEstimateJpaEntity> findCareEstimatesByPetId(@Param("petId") Long petId);
}
