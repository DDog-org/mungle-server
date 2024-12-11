package ddog.user.presentation.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CareReviewListResp {
    private long reviewCount;
    private List<CareReviewSummaryResp> reviewList;
}
