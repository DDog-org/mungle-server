package ddog.daengleserver.infrastructure;

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

    /* Query 문을 통해 PetId와 일치하고 상태가 대기중이어야 하는 견적서만 가져와야 함.
     * 또한 추후에 성능 최적화를 위해 페이징 기법 사용해야함. */
    List<GroomingEstimateJpaEntity> findGroomingEstimateJpaEntitiesByPetId(Long petId);
}
