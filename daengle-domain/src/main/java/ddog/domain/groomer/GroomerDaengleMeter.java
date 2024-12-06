package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomerDaengleMeter {
    private Long groomerDaengleMeterId;
    private Long groomerId;
    private Long score;
    private Long evaluationCount;
}