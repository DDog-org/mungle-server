package ddog.user.presentation.review.dto.response;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GroomingReviewDetailResp {
    private Long groomingReviewId;
    private Long groomerId;
    private List<GroomingKeyword> groomingKeywordList;
    private String revieweeName;
    private String shopName;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
