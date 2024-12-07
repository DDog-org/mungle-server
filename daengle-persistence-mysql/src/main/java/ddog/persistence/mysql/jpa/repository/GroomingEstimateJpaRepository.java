package ddog.persistence.mysql.jpa.repository;

import ddog.persistence.mysql.jpa.entity.GroomingEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    GroomingEstimateJpaEntity save(GroomingEstimateJpaEntity estimateJpaEntity);

    Optional<GroomingEstimateJpaEntity> findByEstimateId(Long estimateId);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'GENERAL' AND (g.status = 'NEW' OR g.status = 'PENDING') AND g.address = :address")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByAddress(@Param("address") String address);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'DESIGNATION' AND (g.status = 'NEW' OR g.status = 'PENDING') AND g.groomerId = :groomerId")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByGroomerId(@Param("groomerId") Long groomerId);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.status = 'PENDING' AND g.petId = :petId")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByPetId(@Param("petId") Long petId);
}
