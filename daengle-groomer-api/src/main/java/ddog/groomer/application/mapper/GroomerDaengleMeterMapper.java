package ddog.groomer.application.mapper;

import ddog.domain.groomer.GroomerDaengleMeter;

public class GroomerDaengleMeterMapper {
    public static GroomerDaengleMeter create(Long groomerId) {
        return GroomerDaengleMeter.builder()
                .groomerDaengleMeterId(null)
                .groomerId(groomerId)
                .score(50)
                .evaluationCount(0L)
                .build();
    }
}
