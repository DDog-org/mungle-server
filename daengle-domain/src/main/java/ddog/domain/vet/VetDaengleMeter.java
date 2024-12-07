package ddog.domain.vet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetDaengleMeter {
    private Long vetDaengleMeterId;
    private Long vetId;
    private Long score;
    private Long evaluationCount;
}