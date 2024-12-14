package ddog.user.presentation.review.dto.response;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CareReviewDetailResp {
    private Long careReviewId;
    private Long reservationId;
    private Long vetId;
    private List<CareKeyword> careKeywordList;
    private String revieweeName;
    private String shopName;
    private LocalDateTime schedule;
    private double starRating;
    private String content;
    private List<String> imageUrlList;
}
