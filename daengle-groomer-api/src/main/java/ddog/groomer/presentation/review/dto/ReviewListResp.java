package ddog.groomer.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewListResp {
    private long reviewCount;
    private List<ReviewSummaryResp> reviewList;
}
