package ddog.vet.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SubmitReportReviewResp {
    private Long reviewId;
    private Long reviewerId;
    private Long revieweeId;
}
