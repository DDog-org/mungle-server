package ddog.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CareKeywordReview {
    KIND("친절해요"),
    PROFESSIONAL("전문적이에요"),
    GOOD_AT_EXPLAINING("설명을 잘해줘요"),
    EXCELLENT_CONSULTATION("상담을 잘해줘요"),
    REASONABLE("가격이 적당해요"),
    HYGIENIC("위생적이에요"),
    FOR_OLD_DOGS("노견을 잘 다뤄요"),
    FOR_LARGE_DOGS("대형견을 잘 다뤄요"),
    GOOD_WITH_DANGEROUS_DOGS("맹견을 잘 다뤄요"),
    CARES_FOR_SKIN_DISEASE("피부병 케어를 잘해요"),
    HEART_DISEASE_CARE("심장질환 케어를 잘해요");


    public final String keyword;
}
