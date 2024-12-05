package ddog.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomingKeywordReview {
    EXCELLENT_CONSULTATION("상담을 잘해줘요"),
    HYGIENIC("위생적이에요"),
    CUSTOMIZED_CARE("맞춤케어를 잘해줘요"),

    //미용
    STYLE_IS_GREAT("스타일이 최고예요");

    private final String keyword;
}
