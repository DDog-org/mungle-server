package ddog.domain.groomer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroomingKeyword {
    HYGIENIC("위생적이에요", null),
    EXCELLENT_CONSULTATION("상담을 잘해 줘요", null),
    STYLE_IS_GREAT("스타일이 멋져요", null),
    KIND("친절해요", null),
    SKILL("솜씨가 좋아요", null),
    REASONABLE("가격이 적당해요", null),
    GOOD_AT_EXPLAINING("설명을 잘해줘요", null),
    PROFESSIONAL("전문적이에요", null),
    FOR_OLD_DOGS("노견을 잘 다뤄요", GroomingBadge.OLD_DOG),
    FOR_LARGE_DOGS("대형견을 잘 다뤄요", GroomingBadge.LARGE_DOG),
    GOOD_WITH_DANGEROUS_DOGS("맹견을 잘 다뤄요", GroomingBadge.DANGEROUS_DOG),
    CARES_FOR_SKIN_DISEASE("피부병 케어를 잘해요", GroomingBadge.SKIN_DISEASE);

    private final String keyword;
    private final GroomingBadge badge;
}
