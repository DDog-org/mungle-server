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

    public static void validateRequirements(String requirements) {
        if (requirements == null) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is null.");
        }

        if (requirements.isEmpty() || requirements.length() > 400) {
            throw new IllegalArgumentException("Invalid Requirements: Requirements is empty or exceeds 400 characters.");
        }
    }

    public static void validateDiagnosis(String diagnosis) {
        if (diagnosis == null) {
            throw new IllegalArgumentException("Invalid Diagnosis: Diagnosis is null.");
        }

        if (diagnosis.isEmpty() || diagnosis.length() > 50) {
            throw new IllegalArgumentException("Invalid Diagnosis: Diagnosis is empty or exceeds 50 characters.");
        }
    }

    public static void validateCause(String cause) {
        if (cause == null) {
            throw new IllegalArgumentException("Invalid Cause: Cause is null.");
        }

        if (cause.isEmpty() || cause.length() > 50) {
            throw new IllegalArgumentException("Invalid Cause: Cause is empty or exceeds 400 characters.");
        }
    }

    public static void validateTreatment(String treatment) {
        if (treatment == null) {
            throw new IllegalArgumentException("Invalid Treatment: Treatment is null.");
        }

        if (treatment.isEmpty() || treatment.length() > 50) {
            throw new IllegalArgumentException("Invalid Treatment: Treatment is empty or exceeds 400 characters.");
        }
    }
}
