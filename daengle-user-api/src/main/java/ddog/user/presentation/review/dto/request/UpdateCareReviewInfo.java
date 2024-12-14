package ddog.user.presentation.review.dto.request;

import ddog.domain.vet.enums.CareKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UpdateCareReviewInfo {
    private Long starRating;
    private List<CareKeyword> careKeywordList;
    private String content;
    private List<String> imageUrlList;
}
