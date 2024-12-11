package ddog.user.presentation.review.dto.response;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GroomingReviewSummaryResp {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeyword> groomingKeywordList;
    private String reviewerName;
    private String reviewerImageUrl;
    private String revieweeName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
