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
public class GroomingEstimate {

    private Long groomingEstimateId;
    private Long userId;
    private Long petId;
    private Long groomerId;

    private String address;
    private LocalDateTime reservedDate;
    private String desiredStyle;
    private String requirements;
    private String overallOpinion;
    private Proposal proposal;
    private EstimateStatus status;
    private LocalDateTime createdAt;

    public static void validateRequirements(String requirements) {
        if (requirements == null) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is null.");
        }

        if (requirements.isEmpty() || requirements.length() > 400) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is empty or exceeds 400 characters.");
        }
    }

    public static void validateOverallOpinion(String overallOpinion) {
        if (overallOpinion == null) {
            throw new IllegalArgumentException("Invalid Overall Opinion: Overall Opinion is null.");
        }

        if (overallOpinion.isEmpty() || overallOpinion.length() > 400) {
            throw new IllegalArgumentException("Invalid Overall Opinion: Overall Opinion is empty or exceeds 400 characters.");
        }
    }
}
