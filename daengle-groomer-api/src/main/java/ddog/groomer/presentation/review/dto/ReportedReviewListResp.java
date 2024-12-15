package ddog.groomer.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReportedReviewListResp {
    private long reviewCount;
    private List<ReportedReviewSummaryResp> reviewList;
}
