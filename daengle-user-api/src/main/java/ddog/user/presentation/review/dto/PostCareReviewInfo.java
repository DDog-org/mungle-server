package ddog.user.presentation.review.dto;

import ddog.domain.review.enums.CareKeywordReview;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostCareReviewInfo {
    private Long reservationId;
    private Long starRating;
    private List<CareKeywordReview> careKeywordReviewList;
    private String content;
    private List<String> imageUrlList;
}
