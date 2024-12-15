package ddog.vet.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportReviewResp {
    private Long reviewId;
    private Long reviewerId;
    private Long revieweeId;
}
