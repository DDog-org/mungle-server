package ddog.domain.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroomingEstimate extends Estimate {

    private Long groomerId;

    private String desiredStyle;
    private String overallOpinion;

    public static void validateOverallOpinion(String overallOpinion) {
        if (overallOpinion == null) {
            throw new IllegalArgumentException("Invalid Overall Opinion: Overall Opinion is null.");
        }

        if (overallOpinion.isEmpty() || overallOpinion.length() > 400) {
            throw new IllegalArgumentException("Invalid Overall Opinion: Overall Opinion is empty or exceeds 400 characters.");
        }


    }


}
