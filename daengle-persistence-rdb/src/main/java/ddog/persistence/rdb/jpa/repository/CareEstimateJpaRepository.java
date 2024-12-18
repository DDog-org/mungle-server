package ddog.persistence.rdb.jpa.repository;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.persistence.rdb.jpa.entity.CareEstimateJpaEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CareEstimateJpaRepository extends JpaRepository<CareEstimateJpaEntity, Long> {
    CareEstimateJpaEntity save(CareEstimateJpaEntity careEstimate);

    Optional<CareEstimateJpaEntity> findByEstimateId(Long estimateId);

    @Modifying
    @Transactional
    @Query("UPDATE CareEstimates c SET c.status = :status WHERE c.estimateId = :parentId")
    void updateParentEstimateByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    @Modifying
    @Transactional
    @Query("UPDATE CareEstimates c SET c.status = :status WHERE c.parentId = :parentId")
    void updateStatusByParentId(@Param("status") EstimateStatus status, @Param("parentId") Long parentId);

    Optional<CareEstimateJpaEntity> findTopByStatusAndProposalAndPetId(EstimateStatus status, Proposal proposal, Long petId);

    @Query("SELECT c.estimateId as estimateId, c.petId as petId, p.imageUrl as imageUrl, p.name as name " +
            "FROM CareEstimates c JOIN Pets p ON c.petId = p.petId " +
            "WHERE c.userId = :userId AND c.status = :status AND c.proposal = :proposal")
    List<Tuple> findByStatusAndProposalAndUserId(EstimateStatus status, Proposal proposal, Long userId);

    Page<CareEstimateJpaEntity> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    Page<CareEstimateJpaEntity> findByStatusAndProposalAndAddress(EstimateStatus status, Proposal proposal, String address, Pageable pageable);

    Page<CareEstimateJpaEntity> findByStatusAndProposalAndVetId(EstimateStatus status, Proposal proposal, Long vetId, Pageable pageable);

    List<CareEstimateJpaEntity> findCareEstimatesByVetIdAndStatus(Long vetAccountId, EstimateStatus status);

    List<CareEstimateJpaEntity> findCareEstimatesByVetIdAndProposal(Long vetAccountId, Proposal proposal);

    List<CareEstimateJpaEntity> findByVetId(Long vetAccountId);

    @Query("SELECT g FROM CareEstimates g " +
            "WHERE g.reservedDate >= :startOfDay " +
            "AND g.reservedDate < :endOfDay " +
            "AND g.vetId = :vetId " +
            "AND g.status = :status")
    List<CareEstimateJpaEntity> finTodayScheduleByVetId(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("vetId") Long vetId,
            @Param("status") EstimateStatus status);


    List<CareEstimateJpaEntity> findCareEstimateJpaEntitiesByUserId(Long userId);

    Optional<CareEstimateJpaEntity> findCareEstimateJpaEntityByUserIdAndVetId(Long userId, Long vetId);

    @Query("SELECT COUNT(DISTINCT c.parentId) " +
            "FROM CareEstimates c " +
            "WHERE c.vetId = :vetAccountId")
    Integer countDistinctParentIdsByVetAccountId(@Param("vetAccountId") Long vetAccountId);

    Optional<CareEstimateJpaEntity> findCareEstimateJpaEntityByUserIdAndPetId(Long userId, Long petId);

    List<CareEstimateJpaEntity> findCareEstimateJpaEntitiesByUserIdAndStatus(Long userId, EstimateStatus status);

}
