package ddog.persistence.mysql.jpa.repository;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.persistence.mysql.jpa.entity.GroomingEstimateJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    GroomingEstimateJpaEntity save(GroomingEstimateJpaEntity estimateJpaEntity);

    Optional<GroomingEstimateJpaEntity> findByEstimateId(Long estimateId);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'GENERAL' AND g.status = 'NEW' AND g.address = :address")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByAddress(@Param("address") String address);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.proposal = 'DESIGNATION' AND g.status = 'NEW' AND g.groomerId = :groomerId")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByGroomerId(@Param("groomerId") Long groomerId);

    @Query("SELECT g FROM GroomingEstimates g " +
            "WHERE g.status = 'PENDING' AND g.petId = :petId")
    List<GroomingEstimateJpaEntity> findGroomingEstimatesByPetId(@Param("petId") Long petId);

    @Modifying
    @Transactional
    @Query("UPDATE GroomingEstimates g SET g.status = :status WHERE g.estimateId = :parentId")
    void updateParentEstimateByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    @Modifying
    @Transactional
    @Query("UPDATE GroomingEstimates g SET g.status = :status WHERE g.parentId = :parentId")
    void updateStatusByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    Page<GroomingEstimateJpaEntity> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END " +
            "FROM GroomingEstimates g WHERE g.status = 'NEW' AND g.proposal = 'GENERAL' AND g.petId = :petId")
    boolean existsNewAndGeneralByPetId(@Param("petId") Long petId);

    Optional<GroomingEstimateJpaEntity> findTopByStatusAndProposalAndPetId(EstimateStatus status, Proposal proposal, Long petId);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END " +
            "FROM GroomingEstimates g WHERE g.status = 'NEW' AND g.proposal = 'DESIGNATION' AND g.petId = :petId")
    boolean existsNewAndDesignationByPetId(@Param("petId") Long petId);
}
