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
public class CareEstimate {

    private Long careEstimateId;
    private Long userId;
    private Long petId;
    private Long vetId;

    private String address;
    private LocalDateTime reservedDate;
    private String symptoms;
    private String requirements;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private String diagnosis;
    private String cause;
    private String treatment;
}
