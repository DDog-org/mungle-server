package ddog.daengleserver.infrastructure;

import ddog.daengleserver.infrastructure.jpa.GroomingEstimateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroomingEstimateJpaRepository extends JpaRepository<GroomingEstimateJpaEntity, Long> {

    /* Query 문을 통해 일반/지정 미용사 및 같은 지역의 사람들한테만 뜨도록 쿼리 추가해야함.
    * 또한 추후에 성능 최적화를 위해 페이징 기법 사용해야함. */
    List<GroomingEstimateJpaEntity> findGroomingEstimatesJpaEntitiesByAddress(String address);

    GroomingEstimateJpaEntity getGroomingEstimateJpaEntityByGroomingEstimateId(Long groomingEstimateId);
}
