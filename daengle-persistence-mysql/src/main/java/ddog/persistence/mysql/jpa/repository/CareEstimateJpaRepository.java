package ddog.persistence.mysql.jpa.repository;

import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.persistence.mysql.jpa.entity.CareEstimateJpaEntity;
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

    Page<CareEstimateJpaEntity> findByPetIdAndStatusAndProposal(Long petId, EstimateStatus status, Proposal proposal, Pageable pageable);

    Page<CareEstimateJpaEntity> findByStatusAndProposalAndAddress(EstimateStatus status, Proposal proposal, String address, Pageable pageable);

    Page<CareEstimateJpaEntity> findByStatusAndProposalAndVetId(EstimateStatus status, Proposal proposal, Long vetId, Pageable pageable);

    List<CareEstimateJpaEntity> findCareEstimatesByVetIdAndStatus(Long vetAccountId, EstimateStatus status);

    List<CareEstimateJpaEntity> findCareEstimatesByVetIdAndProposal(Long vetAccountId, Proposal proposal);

    List<CareEstimateJpaEntity> findByVetId(Long vetAccountId);

    @Query("SELECT c FROM CareEstimates c WHERE DATE(c.reservedDate) = :today AND c.vetId = :vetAccountId AND c.status = :status")
    List<CareEstimateJpaEntity> finTodayScheduleByVetId(LocalDate today, Long vetAccountId, EstimateStatus status);

    List<CareEstimateJpaEntity> findCareEstimateJpaEntitiesByUserId(Long userId);

    Optional<CareEstimateJpaEntity> findCareEstimateJpaEntityByUserIdAndVetId(Long userId, Long vetId);

    @Query("SELECT COUNT(DISTINCT c.parentId) " +
            "FROM CareEstimates c " +
            "WHERE c.vetId = :vetAccountId")
    Integer countDistinctParentIdsByVetAccountId(@Param("vetAccountId") Long vetAccountId);

    Optional<CareEstimateJpaEntity> findCareEstimateJpaEntityByUserIdAndPetId(Long userId, Long petId);
}
