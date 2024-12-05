package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CareEstimate extends Estimate {

    private Long vetId;

    private String symptoms;
    private String diagnosis;
    private String cause;
    private String treatment;

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
