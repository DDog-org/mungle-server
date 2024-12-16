package ddog.user.presentation.review.dto.request;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostGroomingReviewInfo {
    private Long reservationId;
    private Integer starRating;
    private List<GroomingKeyword> groomingKeywordList;
    private String content;
    private List<String> imageUrlList;
}
