package ddog.groomer.presentation.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportReviewResp {
    private String reviewerNickName;
    private String reviewerImageUrl;
}