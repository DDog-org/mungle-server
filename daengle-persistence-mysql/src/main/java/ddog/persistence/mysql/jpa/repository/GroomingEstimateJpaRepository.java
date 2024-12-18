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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    GroomingEstimateJpaEntity save(GroomingEstimateJpaEntity estimateJpaEntity);

    Optional<GroomingEstimateJpaEntity> findByEstimateId(Long estimateId);

    List<GroomingEstimateJpaEntity> findGroomingEstimateJpaEntitiesByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE GroomingEstimates g SET g.status = :status WHERE g.estimateId = :parentId")
    void updateParentEstimateByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    @Modifying
    @Transactional
    @Query("UPDATE GroomingEstimates g SET g.status = :status WHERE g.parentId = :parentId")
    void updateStatusByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    Page<GroomingEstimateJpaEntity> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    Optional<GroomingEstimateJpaEntity> findTopByStatusAndProposalAndPetId(EstimateStatus status, Proposal proposal, Long petId);

    Page<GroomingEstimateJpaEntity> findByStatusAndProposalAndAddress(EstimateStatus status, Proposal proposal, String address, Pageable pageable);

    Page<GroomingEstimateJpaEntity> findByStatusAndProposalAndGroomerId(EstimateStatus status, Proposal proposal, Long groomerId, Pageable pageable);

    List<GroomingEstimateJpaEntity> findAllByGroomerIdAndStatus(Long groomerId, EstimateStatus status);

    List<GroomingEstimateJpaEntity> findAllByGroomerIdAndProposal(Long groomerId, Proposal proposal);

    List<GroomingEstimateJpaEntity> findAllByGroomerId(Long groomerId);

    @Query("SELECT g FROM GroomingEstimates g WHERE DATE(g.reservedDate) = :today AND g.groomerId = :groomerAccountId AND g.status = :status")
    List<GroomingEstimateJpaEntity> findTodayScheduleByGroomerId(LocalDate today, Long groomerAccountId, EstimateStatus status);

    Optional<GroomingEstimateJpaEntity> findGroomingEstimateJpaEntityByPetIdAndGroomerId(Long petId, Long groomerId);

    @Query("SELECT COUNT(DISTINCT c.parentId) " +
            "FROM GroomingEstimates c " +
            "WHERE c.groomerId = :groomerAccountId")
    Integer countDistinctParentIdsByGroomerAccountId(@Param("groomerAccountId") Long groomerAccountId);
}
