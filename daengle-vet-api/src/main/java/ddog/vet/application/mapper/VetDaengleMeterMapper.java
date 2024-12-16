package ddog.vet.application.mapper;

import ddog.domain.vet.VetDaengleMeter;

public class VetDaengleMeterMapper {
    public static VetDaengleMeter create(Long vetId) {
        return VetDaengleMeter.builder()
                .vetDaengleMeterId(null)
                .vetId(vetId)
                .score(50)
                .evaluationCount(0L)
                .build();
    }
}
