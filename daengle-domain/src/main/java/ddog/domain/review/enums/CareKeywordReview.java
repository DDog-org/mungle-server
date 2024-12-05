package ddog.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CareKeywordReview {
    EXCELLENT_CONSULTATION("상담을 잘해줘요"),
    HYGIENIC("위생적이에요"),
    CUSTOMIZED_CARE("맞춤케어를 잘해줘요"),

    //진료
    PROFESSIONAL("전문성이 돋보여요");

    public final String keyword;
}
