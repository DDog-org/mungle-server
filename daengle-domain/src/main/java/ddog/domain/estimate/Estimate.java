package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Estimate {

    private Long estimateId;
    private Long userId;
    private Long petId;
    private String address;
    private LocalDateTime reservedDate;
    private String requirements;
    private EstimateStatus status;
    private Proposal proposal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static void validateRequirements(String requirements) {
        if (requirements == null) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is null.");
        }

        if (requirements.isEmpty() || requirements.length() > 400) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is empty or exceeds 400 characters.");
        }
    }

}
