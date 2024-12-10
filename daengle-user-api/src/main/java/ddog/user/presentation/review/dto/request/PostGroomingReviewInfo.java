package ddog.user.presentation.review.dto.request;

import ddog.domain.review.enums.GroomingKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostGroomingReviewInfo {
    private Long reservationId;
    private Long starRating;
    private List<GroomingKeywordReview> groomingKeywordReviewList;
    private String content;
    private List<String> imageUrlList;
}
