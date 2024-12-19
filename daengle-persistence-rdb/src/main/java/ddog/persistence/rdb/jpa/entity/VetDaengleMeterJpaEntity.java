package ddog.persistence.rdb.jpa.entity;

import ddog.domain.vet.VetDaengleMeter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "VetDangleMeters")
public class VetDaengleMeterJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long vetDaengleMeterId;

    Long vetId;
    Integer score;
    Long evaluationCount;

    public VetDaengleMeter toModel() {
        return VetDaengleMeter.builder()
                .vetDaengleMeterId(vetDaengleMeterId)
                .vetId(vetId)
                .score(score)
                .evaluationCount(evaluationCount)
                .build();
    }

    public static VetDaengleMeterJpaEntity from(VetDaengleMeter vetDangleMeter) {
        return VetDaengleMeterJpaEntity.builder()
                .vetDaengleMeterId(vetDangleMeter.getVetDaengleMeterId())
                .vetId(vetDangleMeter.getVetId())
                .score(vetDangleMeter.getScore())
                .evaluationCount(vetDangleMeter.getEvaluationCount())
                .build();
    }
}
