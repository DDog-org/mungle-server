package ddog.persistence.rdb.jpa.entity;

import ddog.domain.groomer.GroomerDaengleMeter;
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
@Entity(name = "GroomerDangleMeters")
public class GroomerDanegleMeterJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long groomerDaengleMeterId;

    Long groomerId;
    Integer score;
    Long evaluationCount;

    public GroomerDaengleMeter toModel() {
        return GroomerDaengleMeter.builder()
                .groomerDaengleMeterId(groomerDaengleMeterId)
                .groomerId(groomerId)
                .score(score)
                .evaluationCount(evaluationCount)
                .build();
    }

    public static GroomerDanegleMeterJpaEntity from(GroomerDaengleMeter groomerDangleMeter) {
        return GroomerDanegleMeterJpaEntity.builder()
                .groomerDaengleMeterId(groomerDangleMeter.getGroomerDaengleMeterId())
                .groomerId(groomerDangleMeter.getGroomerId())
                .score(groomerDangleMeter.getScore())
                .evaluationCount(groomerDangleMeter.getEvaluationCount())
                .build();
    }
}
