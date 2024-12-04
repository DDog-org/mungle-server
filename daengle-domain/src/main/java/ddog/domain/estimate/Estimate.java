package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    private Long estimateId;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
