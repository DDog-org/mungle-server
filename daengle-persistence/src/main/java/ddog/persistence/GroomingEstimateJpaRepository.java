package ddog.persistence;

import ddog.daengleserver.infrastructure.po.GroomingEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'GENERAL' AND g.status = 'NEW' AND g.address = :address")
    List<GroomingEstimateJpaEntity> findGeneralGroomingEstimatesByAddress(@Param("address") String address);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'DESIGNATION' AND g.status = 'NEW' AND g.groomerId = :groomerId")
    List<GroomingEstimateJpaEntity> findDesignationGroomingEstimatesByGroomerId(@Param("groomerId") Long groomerId);

    GroomingEstimateJpaEntity getGroomingEstimateJpaEntityByGroomingEstimateId(Long groomingEstimateId);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.status = 'PENDING' AND g.petId = :petId")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByPetId(@Param("petId") Long petId);
}
